package com.springboot.cloud.sysadmin.organization.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.common.web.entity.po.BasePo;
import com.springboot.cloud.sysadmin.organization.dao.ApplicationMapper;
import com.springboot.cloud.sysadmin.organization.entity.param.ApplicationQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.Application;
import com.springboot.cloud.sysadmin.organization.entity.vo.ApplicationVo;
import com.springboot.cloud.sysadmin.organization.exception.ApplicationNotFoundException;
import com.springboot.cloud.sysadmin.organization.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {

    @Autowired
    private IUserApplicationService userApplicationService;

    @Override
    @Cached(name = "application::", key = "#id", cacheType = CacheType.BOTH)   // @Cached是JetCache提供的一个注解，用于标注在方法上，表示该方法的返回值会被缓存。
    // 它可以用来缓存方法的返回值，以提高方法的执行效率，减少对数据库或其他资源的访问。
    public ApplicationVo get(String id) {
        Application application = this.getById(id);
        if (Objects.isNull(application)) {
            throw new ApplicationNotFoundException("application not found with id:" + id);
        }
        return new ApplicationVo(application);
    }

    @Override
    public List<Application> getAll() {
        return this.list();
    }

    @Override
    public boolean add(Application application) {
        boolean isSuccess = this.save(application);
        return isSuccess;
    }

    @Override
    public IPage<Application> query(Page page, ApplicationQueryParam applicationQueryParam) {
        QueryWrapper<Application> queryWrapper = applicationQueryParam.build();
        queryWrapper.like(StringUtils.isNotBlank(applicationQueryParam.getAppName()), "app_name", applicationQueryParam.getAppName());
        return this.page(page, queryWrapper);
    }

    @Override
//    @Cached(name = "application4user::", key = "#userId", cacheType = CacheType.BOTH)
    public Set<String> query(String userId) {
        Set<String> applicationIds = userApplicationService.queryByUserId(userId);
        List<Application> applications = null;
        if (!CollectionUtils.isEmpty(applicationIds)) {
            applications = (List<Application>) this.listByIds(applicationIds);
            return applications.stream().map(BasePo::getId).collect(Collectors.toSet());
        }else{
            return new HashSet<>();
        }
    }

    @Override
    @CacheInvalidate(name = "application::", key = "#application.id")
    public boolean update(Application application) {
        boolean isSuccess = this.updateById(application);
        return isSuccess;
    }

    @Override
    @CacheInvalidate(name = "application::", key = "#id")
    public boolean delete(String id) {
        return this.removeById(id);
    }

}
