package com.springboot.cloud.nailservice.nail.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.util.UserContextHolder;
import com.springboot.cloud.nailservice.nail.clients.UserClient;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import com.springboot.cloud.nailservice.nail.entity.vo.PredictVo;
import com.springboot.cloud.nailservice.nail.exception.FindNoImagePathFromRedisException;
import com.springboot.cloud.nailservice.nail.exception.PredictionFailedException;
import com.springboot.cloud.nailservice.nail.service.INailService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/nail/model")
@Api("nail/model")
@Slf4j
public class ModelController {

    private final RestTemplate restTemplate;

    @Autowired
    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Resource
    private INailService nailService;

    @Resource
    private UserClient userClient;

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @PostMapping("/predict")
    // TODO 入参不合理 不可以输入带引号的code,直接输入code才ok
    public Result predict(@RequestBody String diagnosisCode) throws IOException {
        return Result.success(predict_service(diagnosisCode));
    }

    public PredictVo predict_service(String diagnosisCode) {

        // 请求参数JSON格式
//        public class PredictParam {
//            private String diagnosisCode;
//            private String imageFile;
//        }
        // 已取消该设计，改为使用Redis存储图片路径

        // 请求地址
        String postUrl = "http://localhost:5000/predict";

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

        // 创建RestTemplate
        RestTemplate restTemplate = new RestTemplate();
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

            String diagResult = mostLikelyClass;

            // 处理返回的信息
            PredictVo predictVo = new PredictVo(diagnosisCode, diagResult);
            if (verifyUserRolePatient()) {
                insertPatient(diagnosisCode, predictVo);
            }
            return predictVo;
        } else {
            throw new PredictionFailedException("Prediction failed");
        }
    }


    private void insertPatient(String diagnosisCode, PredictVo predictVo) {
        NailDiag save = new NailDiag();
        save.setDoctorName("自测");
        save.setPatientName(getUserName());
        save.setResultAccuracy(-1);
        save.setDiagResult(predictVo.getDiagResult());
        save.setImageFile(redisTemplate.boundValueOps("diagnosisCode_imagePath_" + diagnosisCode).get());
        save.setDiagnosisCode(predictVo.getDiagnosisCode());
        try {
            nailService.saveOneDiag(save);
        } catch (Exception e) {
            log.error("患者自测数据未保存成功，{}", e.getMessage());
        }
    }


    private boolean verifyUserRolePatient() {
        String username = getUserName();
        Result<User> user = userClient.getUserByUniqueId(username);
        Set<String> roles = user.getData().getRoles();
        for (String role : roles) {
            if ("PAT".equals(role)) {
                return true;
            }
        }
        return false;
    }

    private String getUserName() {
//        return UserContextHolder.getInstance().getUsername();TODO
        return "test_patient_1";
    }


}
