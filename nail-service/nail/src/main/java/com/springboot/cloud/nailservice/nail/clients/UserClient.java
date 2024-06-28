package com.springboot.cloud.nailservice.nail.clients;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.form.UserForm;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// name: 服务提供者的服务名
// path: 服务提供者的服务路径
// 通过@FeignClient注解的name属性指定服务提供者的服务名，path属性指定服务提供者的服务路径
// 通过@GetMapping注解的value属性指定服务提供者的服务路径
@FeignClient(name = "organization")
public interface UserClient {

    @GetMapping(value = "/user")
    Result<User> getUserByUniqueId(@RequestParam("uniqueId") String userId);

    @PostMapping(value = "/user")
    Result add(@RequestBody @Valid UserForm userForm);
}
