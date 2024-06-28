package com.springboot.cloud.nailservice.nail.clients;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import com.springboot.cloud.nailservice.nail.entity.form.UserForm;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public Result<User> getUserByUniqueId(String uniqueId) {
        return Result.success(new User());
    }

    @Override
    public Result add(UserForm userForm) {
        // 返回一个表示降级的默认响应
        return Result.fail("Service is currently unavailable. Please try again later.");
    }
}
