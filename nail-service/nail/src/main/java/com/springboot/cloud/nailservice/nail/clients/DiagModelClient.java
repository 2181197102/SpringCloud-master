package com.springboot.cloud.nailservice.nail.clients;

import com.alibaba.fastjson.JSON;
import com.springboot.cloud.common.core.entity.vo.Result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Set;

// name: 服务提供者的服务名
// path: 服务提供者的服务路径
// 通过@FeignClient注解的name属性指定服务提供者的服务名，path属性指定服务提供者的服务路径
// 通过@GetMapping注解的value属性指定服务提供者的服务路径
@FeignClient(name = "diag-model-service", path = "/localhost:5000", fallback = DiagModelClientFallback.class)
public interface DiagModelClient {

    @PostMapping(value = "/predict", consumes = "multipart/form-data")
    Result<String> predict(@RequestPart("file1") MultipartFile file1, @RequestPart("file2") MultipartFile file2);

}

//5.使用RestTemplate调用http请求
//
//        (1)Jar包位置
//
//        RestTemplate，全称org.springframework.web.client.RestTemplate。
//
//        本例使用版本。
//
//        dependency>
//<groupId>org.springframework</groupId>
//<artifactId>spring-web</artifactId>
//<version>5.3.15</version>
//<scope>compile</scope>
//</dependency>
//        (2)客户端代码
//
//public class Utils05RestTemplateClient {
//    public static void main(String[] args) throws Exception {
//        f1();
//        f2();
//        f3();
//    }
//    /**
//     * 1.使用RestTemplate调用服务端的POST请求
//     * 服务端入参注解: @RequestBody
//     */
//    public static void f1() throws Exception {
//        // 1.请求URL
//        String postUrl = "http://127.0.0.1:19091/server/comm/f1";
//        // 2.请求参数JSON格式
//        Map<String, String> map = new HashMap<>();
//        map.put("userName", "HangZhou20220718");
//        map.put("tradeName", "Vue进阶教程");
//        String json = JSON.toJSONString(map);
//        // 3.创建RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//        // 4.设置RestTemplate参数(请求头和body)
//        HttpHeaders headers = new HttpHeaders();
//        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(mediaType);
//        headers.add("Accept", "application/json");
//        HttpEntity<String> entity = new HttpEntity<>(json, headers);
//        // 5.使用RestTemplate发起请求与接收返回值
//        String resultData = restTemplate.postForObject(postUrl, entity, String.class);
//        System.out.println("从服务端返回结果: " + resultData);
//    }
//    /**
//     * 2.使用RestTemplate调用服务端的GET请求
//     * 服务端入参注解: @RequestParam
//     */
//    public static void f2() throws Exception {
//        // 1.请求URL与组装请求参数
//        String getUrl = "http://127.0.0.1:19091/server/comm/f2";
//        String obj = "Vue进阶教程";
//        String para = "?obj=" + obj;
//        getUrl = getUrl + para;
//        // 2.创建RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//        // 3.使用RestTemplate发起请求与接收返回值
//        String resultData = restTemplate.getForObject(getUrl, String.class);
//        System.out.println("从服务端返回结果: " + resultData);
//    }
//    /**
//     * 3.使用RestTemplate调用服务端的GET请求
//     * 服务端入参注解: @PathVariable
//     */
//    public static void f3() throws Exception {
//        // 1.请求URL与组装请求参数
//        String getUrl = "http://127.0.0.1:19091/server/comm/f3/";
//        String obj = "Vue进阶教程";
//        getUrl = getUrl + obj;
//        // 2.创建RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//        // 3.使用RestTemplate发起请求与接收返回值
//        String resultData = restTemplate.getForObject(getUrl, String.class);
//        System.out.println("从服务端返回结果: " + resultData);
//    }
//}