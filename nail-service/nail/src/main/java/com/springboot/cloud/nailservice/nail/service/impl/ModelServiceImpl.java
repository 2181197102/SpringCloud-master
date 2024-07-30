package com.springboot.cloud.nailservice.nail.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.clients.UserClient;
import com.springboot.cloud.nailservice.nail.entity.param.PredictParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import com.springboot.cloud.nailservice.nail.entity.vo.PredictVo;
import com.springboot.cloud.nailservice.nail.exception.FindNoImagePathFromRedisException;
import com.springboot.cloud.nailservice.nail.exception.PredictionFailedException;
import com.springboot.cloud.nailservice.nail.service.IModelService;
import com.springboot.cloud.nailservice.nail.service.INailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ModelServiceImpl implements IModelService {

    private final RestTemplate restTemplate;
    private final INailService nailService;
    private final UserClient userClient;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public ModelServiceImpl(RestTemplate restTemplate, INailService nailService, UserClient userClient, RedisTemplate<String, String> redisTemplate) {
        this.restTemplate = restTemplate;
        this.nailService = nailService;
        this.userClient = userClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public PredictVo predict(PredictParam predictParam) throws IOException {

        String diagnosisCode = predictParam.getDiagnosisCode();
        String openId = predictParam.getOpenId();
        String patientName = predictParam.getPatientName();

        // 请求地址
        String postUrl = "http://nail-predict-model:5000/predict";
//        String postUrl = "http://localhost:5000/predict";

        // 从 Redis 中获取存储的值
        String imagePathString = redisTemplate.boundValueOps("diagnosisCode_imagePath_" + diagnosisCode).get();

        // 如果值为空，进行相应的处理
        List<String> filelist = null;
        if (imagePathString != null && !imagePathString.isEmpty()) {
            // 使用逗号分隔符分割字符串，并创建一个 LinkedList 对象
            filelist = new LinkedList<>(Arrays.asList(imagePathString.split(",")));
            // 如果需要对列表进行修改，可以使用 LinkedList 提供的方法，如添加、删除等
        } else {
            // 处理值为空的情况，如记录日志或者抛出异常
            throw new FindNoImagePathFromRedisException("Find no image path from redis");
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (String file : filelist) {
            File file_i = new File(file);
            body.add("file", new FileSystemResource(file_i));
        }

        // TODO: 是否为JSON格式？
        // String json = JSON.toJSONString(body); // 将对象转换为JSON格式的字符串

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body);

        // 使用RestTemplate发起请求与接收返回值
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(postUrl, httpEntity, String.class);

        // 处理返回值
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // 获取返回的JSON数据
            String resultData = responseEntity.getBody();

            // 使用 Gson 解析 JSON 字符串为一个 JsonObject 对象
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(resultData, JsonObject.class);

            // 从 jsonObject 中提取 mostLikelyClass 的信息
            String mostLikelyClass = jsonObject.get("most_likely_class").getAsString();

//            String diagResult = mostLikelyClass;

            // 进行中英文对照
            // ['Psoriatic Nails', 'Paronychia', 'Nail Matrix Nevus', 'Subungual Melanoma', 'Melanonychia', ' Periungual Warts', 'Onychomycosis']
            // ['银屑病甲', '甲沟炎', '甲母痣', '甲黑素瘤', '甲黑线', ' 甲周疣', '甲真菌病']
            Map<String, String> diagnosisMap = new HashMap<>();
            diagnosisMap.put("Psoriatic Nails", "银屑病甲");
            diagnosisMap.put("Paronychia", "甲沟炎");
            diagnosisMap.put("Nail Matrix Nevus", "甲母痣");
            diagnosisMap.put("Subungual Melanoma", "甲黑素瘤");
            diagnosisMap.put("Melanonychia", "甲黑线");
            diagnosisMap.put("Periungual Warts", "甲周疣");
            diagnosisMap.put("Onychomycosis", "甲真菌病");

            String diagResult = diagnosisMap.getOrDefault(mostLikelyClass, "未知诊断结果");




            // 处理返回的信息
            PredictVo predictVo = new PredictVo(diagnosisCode, diagResult, openId, patientName);
            insertPatient(predictVo);
            return predictVo;
        } else {
            throw new PredictionFailedException("Prediction failed");
        }
    }

    private void insertPatient(PredictVo predictVo) {
        NailDiag save = new NailDiag();
        save.setOpenId(predictVo.getOpenId());
        save.setDoctorName(verifyUserRolePatient(predictVo.getOpenId()) ? "患者角色_自测" : "医生角色_自测");
        save.setPatientName(predictVo.getPatientName());
        save.setResultAccuracy(-1);
        save.setDiagResult(predictVo.getDiagResult());
        save.setImageFile(redisTemplate.boundValueOps("diagnosisCode_imagePath_" + predictVo.getDiagnosisCode()).get());
        save.setDiagnosisCode(predictVo.getDiagnosisCode());
        save.setFeedBack("");
        try {
            nailService.saveOneDiag(save);
        } catch (Exception e) {
            log.error("患者自测数据未保存成功，{}", e.getMessage());
        }
    }

    private boolean verifyUserRolePatient(String openid) {   //注册用户时便使用openid作为username以及password，便直接使用openid查询用户
//        String username = getUserName();
        String username = openid;
        Result<User> user = userClient.getUserByUniqueId(username);
        Set<String> roles = user.getData().getRoles();
        for (String role : roles) {
            if ("PAT".equals(role)) {
                return true;
            }
        }
        return false;
    }

//    private boolean verifyUserRolePatient() {
//        String username = getUserName();
//        Result<User> user = userClient.getUserByUniqueId(username);
//        Set<String> roles = user.getData().getRoles();
//        for (String role : roles) {
//            if ("PAT".equals(role)) {
//                return true;
//            }
//        }
//        return false;
//    }

//    private String getUserName() {
//        // return UserContextHolder.getInstance().getUsername(); TODO
//        return "test_patient_1";
//    }
}
