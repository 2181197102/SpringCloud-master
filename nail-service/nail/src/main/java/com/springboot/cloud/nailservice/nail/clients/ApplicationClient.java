package com.springboot.cloud.nailservice.nail.clients;


import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.pojo.Application;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name: 服务提供者的服务名
// path: 服务提供者的服务路径
// 通过@FeignClient注解的name属性指定服务提供者的服务名，path属性指定服务提供者的服务路径
// 通过@GetMapping注解的value属性指定服务提供者的服务路径
@FeignClient(name = "organization", path = "/app", fallback = ApplicationClientFallback.class)
public interface ApplicationClient {
    @GetMapping(value = "/{id}")
    Result<Application> getAppById(@PathVariable("id") String id);

}


/*
        对应organization服务的ApplicationController.java
        @ApiOperation(value = "获取应用", notes = "根据应用id获取应用")
        @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "String", paramType = "path")
        @GetMapping(value = "/{id}")
        public Result get(@PathVariable String id) {
            log.debug("get with id:{}", id);
            return Result.success(applicationService.get(id));
        }
*/