package com.springboot.cloud.nailservice.nail.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.util.UserContextHolder;
import com.springboot.cloud.nailservice.nail.clients.UserClient;
import com.springboot.cloud.nailservice.nail.entity.param.PredictParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import com.springboot.cloud.nailservice.nail.entity.vo.PredictVo;
import com.springboot.cloud.nailservice.nail.exception.PredictionFailedException;
import com.springboot.cloud.nailservice.nail.service.INailService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


    @PostMapping("/predict")
    public Result predict(@RequestBody PredictParam predictParam) throws IOException {
        return Result.success(predict_service(predictParam));
    }

    public PredictVo predict_service(PredictParam predictParam) {

        // 请求地址
        String postUrl = "http://localhost:5000/predict";

        // 请求参数JSON格式
//        public class PredictParam {
//            private String diagnosisCode;
//            private String imageFile;
//        }
        List<String> filelist = Arrays.asList(predictParam.getImageFile().split(","));
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

        // TODO: 处理返回值
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
            PredictVo predictVo = new PredictVo(predictParam.getDiagnosisCode(), diagResult);
            if (verifyUserRolePatient()) {
                insertPatient(predictParam, predictVo);
            }
            return predictVo;
        } else {
            throw new PredictionFailedException("Prediction failed");
        }
    }


    private void insertPatient(PredictParam predictParam, PredictVo predictVo) {
        NailDiag save = new NailDiag();
        save.setDoctorName("自测");
        save.setPatientName(getUserName());
        save.setResultAccuracy(-1);
        save.setDiagResult(predictVo.getDiagResult());
        save.setImageFile(predictParam.getImageFile());
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
        return UserContextHolder.getInstance().getUsername();
    }


}
