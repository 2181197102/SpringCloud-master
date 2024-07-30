package com.springboot.cloud.nailservice.nail.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Api("wx")
@Slf4j
public class OpenIdController {

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @ResponseBody
    @RequestMapping("/nail/fetchopenid")
    public ResponseEntity<String> getUserInfo(@RequestParam(name = "code") String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=" + appid;
        url += "&secret=" + secret;
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";

        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;

        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setRedirectsEnabled(false)
                    .build();
            httpget.setConfig(requestConfig);
            response = httpClient.execute(httpget);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                res = EntityUtils.toString(responseEntity);
            }

            JSONObject jo = JSON.parseObject(res);
            String openid = jo.getString("openid");
            return ResponseEntity.ok(openid);
        } catch (Exception e) {
            log.error("Error occurred while getting user info: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while getting user info");
        } finally {
            // 释放资源
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException ioException) {
                log.error("Error occurred while closing resources: ", ioException);
            }
        }
    }
}
