package com.springboot.cloud.sysadmin.organization.service;

import java.util.Set;
public interface IUserApplicationService {

        /**
        * 给用户添加应用
        *
        * @param userId
        * @param applicationIds
        * @return
        */
        boolean saveBatch(String userId, Set<String> applicationIds);

        /**
        * 删除用户拥有的应用
        *
        * @param userId
        * @return
        */
        boolean removeByUserId(String userId);

        /**
        * 根据userId查询用户拥有应用id集合
        *
        * @param userId
        * @return
        */
        Set<String> queryByUserId(String userId);
}
