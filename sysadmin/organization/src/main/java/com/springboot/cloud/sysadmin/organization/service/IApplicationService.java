package com.springboot.cloud.sysadmin.organization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.cloud.sysadmin.organization.entity.param.ApplicationQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.Application;
import com.springboot.cloud.sysadmin.organization.entity.vo.ApplicationVo;

import java.util.List;
import java.util.Set;

public interface IApplicationService {
    /**
     * 获取应用
     *
     * @param id 应用id
     * @return ApplicationVo
     */
    ApplicationVo get(String id);

    /**
     * 获取所有应用
     *
     * @return
     */
    List<Application> getAll();

    /**
     * 新增应用
     *
     * @param application
     * @return
     */
    boolean add(Application application);

    /**
     * 查询应用
     *
     * @return
     */
    IPage<Application> query(Page page, ApplicationQueryParam applicationQueryParam);

    /**
     * 根据用户id查询用户可使用的应用
     *
     * @return
     */
    Set<String> query(String userId);

    /**
     * 更新应用信息
     *
     * @param
     */
    boolean update(Application application);

    /**
     * 根据id删除应用
     *
     * @param id
     */
    boolean delete(String id);

}
