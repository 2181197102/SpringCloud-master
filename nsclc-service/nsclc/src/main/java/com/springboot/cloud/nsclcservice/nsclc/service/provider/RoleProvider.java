package com.springboot.cloud.nsclcservice.nsclc.service.provider;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nsclcservice.nsclc.service.provider.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "organization")
public interface RoleProvider {
    @GetMapping(value = "/user")
    Result<User> queryRolesByUserId(@RequestParam("uniqueId") String userId);

}
