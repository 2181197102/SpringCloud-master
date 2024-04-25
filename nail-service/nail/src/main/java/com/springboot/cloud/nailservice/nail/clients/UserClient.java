package com.springboot.cloud.nailservice.nail.clients;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

// name: 服务提供者的服务名
// path: 服务提供者的服务路径
// 通过@FeignClient注解的name属性指定服务提供者的服务名，path属性指定服务提供者的服务路径
// 通过@GetMapping注解的value属性指定服务提供者的服务路径
@FeignClient(name = "organization")
public interface UserClient {
    @GetMapping(value = "/user")
    Result<User> getUserByUniqueId(@RequestParam("uniqueId") String userId);

}



/*
    对应organization服务的UserController.java
    @ApiOperation(value = "获取用户", notes = "获取指定用户信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "string")
    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(userService.get(id));
    }

    @ApiOperation(value = "获取用户", notes = "根据用户唯一标识（username or mobile）获取用户信息")
    @ApiImplicitParam(paramType = "query", name = "uniqueId", value = "用户唯一标识", required = true, dataType = "string")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    @GetMapping
    public Result query(@RequestParam String uniqueId) {
        log.debug("query with username or mobile:{}", uniqueId);
        return Result.success(userService.getByUniqueId(uniqueId));
    }
*/