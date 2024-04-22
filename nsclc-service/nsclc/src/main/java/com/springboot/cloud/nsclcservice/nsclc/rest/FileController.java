package com.springboot.cloud.nsclcservice.nsclc.rest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月15日 7:47 PM
 **/
@Controller
@RequestMapping("/nsclc/download")
@CrossOrigin(origins = "*",maxAge = 3600)
public class FileController {

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping("/nrrd/{diagnosisCode}")
    public ResponseEntity<byte[]> downloadNrrdFile(@PathVariable String diagnosisCode) throws Exception {



        // 🐳🐳🐳设置响应头,把文件名称放入响应头中,确保文件可下载
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment;filename=" + URLEncoder.encode("cc_nj_67_0-20240413010320.nrrd", "UTF-8"));

        String location = redisTemplate.boundValueOps("diagnosisCode_imagePath_" + diagnosisCode).get();
        // 获取File对象
        File file = new File(location);
        Path path = Paths.get(file.toURI());
        // 获取File对象的字节码文件
        byte[] bytes = Files.readAllBytes(path);

        /*
         * 💪💪💪表示返回一个字节码类型的响应
         * 同时设置了响应头和状态码
         * */
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        // 👍👍👍还可以使用下面这种链式调用的方式
        // return ResponseEntity.ok().headers(headers).body(bytes);
    }


}
