package com.springboot.cloud.nailservice.nail.clients;

import com.springboot.cloud.common.core.entity.vo.Result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
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

//TODO: openfeign调用还是在controller中