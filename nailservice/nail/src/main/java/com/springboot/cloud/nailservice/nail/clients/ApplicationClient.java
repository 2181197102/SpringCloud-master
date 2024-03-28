package com.springboot.cloud.nailservice.nail.clients;


import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.pojo.Application;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "organization")
public interface ApplicationClient {
    @GetMapping(value = "/app")
    Result<Application> findByid(@PathVariable("id") String id);
}
