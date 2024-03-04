package com.springboot.cloud.sysadmin.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.sysadmin.organization.dao.UserApplicationMapper;
import com.springboot.cloud.sysadmin.organization.entity.po.UserApplication;
import com.springboot.cloud.sysadmin.organization.service.IUserApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserApplicationService extends ServiceImpl<UserApplicationMapper, UserApplication> implements IUserApplicationService {

    @Override
    @Transactional
    public boolean saveBatch(String userId, Set<String> applicationIds) {
        if (CollectionUtils.isEmpty(applicationIds))
            return false;
        removeByUserId(userId);
        Set<UserApplication> userApplications = applicationIds.stream().map(applicationId -> new UserApplication(userId, applicationId)).collect(Collectors.toSet());
        return saveBatch(userApplications);
    }

    @Override
    @Transactional
    public boolean removeByUserId(String userId) {
        QueryWrapper<UserApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserApplication::getUserId, userId);
        return remove(queryWrapper);
    }

    @Override
    public Set<String> queryByUserId(String userId) {
        QueryWrapper<UserApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserApplication> userApplicationList = list(queryWrapper);
        return userApplicationList.stream().map(UserApplication::getApplicationId).collect(Collectors.toSet());
    }
}
