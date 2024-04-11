package com.springboot.cloud.nailservice.nail.clients;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.pojo.Application;

public class ApplicationClientFallback implements ApplicationClient{
    @Override
    public Result<Application> getAppById(String id) {
        return Result.fail("调用失败");
    }
}
