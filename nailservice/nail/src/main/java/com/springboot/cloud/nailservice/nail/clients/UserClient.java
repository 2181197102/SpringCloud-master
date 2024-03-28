package com.springboot.cloud.nailservice.nail.clients;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.pojo.Application;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "organization")
public interface UserClient {

    @GetMapping(value = "/user/{id}")
    Result<User> findByid(@PathVariable("id") String id);

    @GetMapping(value = "/user")
    Result<User> getUserByUniqueId(@RequestParam("uniqueId") String uniqueId);

    @GetMapping(value = "/app/user/{userId}")
    Result<Set<Application>> queryApplicationsByUserId(@PathVariable("userId") String userId);
}
