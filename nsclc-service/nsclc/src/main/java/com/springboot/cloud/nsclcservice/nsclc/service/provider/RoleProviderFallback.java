package com.springboot.cloud.nsclcservice.nsclc.service.provider;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nsclcservice.nsclc.service.provider.entity.User;
import org.springframework.stereotype.Component;



@Component
public class RoleProviderFallback implements RoleProvider {

    @Override
    public Result<User> queryRolesByUserId(String uniqueId) {
        return Result.success(new User());
    }

}
