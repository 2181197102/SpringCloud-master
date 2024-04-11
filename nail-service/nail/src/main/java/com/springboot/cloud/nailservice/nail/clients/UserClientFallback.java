package com.springboot.cloud.nailservice.nail.clients;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;

public class UserClientFallback implements UserClient{
    @Override
    public Result<User> getUserByUniqueId(String uniqueId) {
        return Result.fail("调用失败");
    }

    @Override
    public Result<User> getUserById(String id) {
        return Result.fail("调用失败");
    }
}
