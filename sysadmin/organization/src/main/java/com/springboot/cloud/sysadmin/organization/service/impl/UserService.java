package com.springboot.cloud.sysadmin.organization.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.sysadmin.organization.dao.UserMapper;
import com.springboot.cloud.sysadmin.organization.entity.param.UserQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.User;
import com.springboot.cloud.sysadmin.organization.entity.vo.UserVo;
import com.springboot.cloud.sysadmin.organization.exception.UserNotFoundException;
import com.springboot.cloud.sysadmin.organization.service.IRoleService;
import com.springboot.cloud.sysadmin.organization.service.IUserApplicationService;
import com.springboot.cloud.sysadmin.organization.service.IUserRoleService;
import com.springboot.cloud.sysadmin.organization.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service    // @Service是Spring框架提供的一个注解，用于标注在类上，表示将该类定义为Spring容器中的一个服务组件（Service Component）。
            // 它是一个专门用于业务逻辑层（Business Service Layer）的注解，表明该类主要用于执行业务操作、事务处理等。
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserApplicationService userApplicationService;

    @Resource
    private IRoleService roleService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override   // @Override是一个标识性注解，它可以用来标识一个方法是覆盖父类的方法，或者实现接口的方法。
                // 当一个方法被@Override标识后，如果父类的方法发生了变化（比如方法名拼写错误、参数变化等），编译器会报错。
    @Transactional  // @Transactional是Spring框架提供的一个注解，用于标注在类或方法上，表示该类或方法需要事务支持。
                    // 它可以用来标识一个类或方法需要被Spring框架进行事务管理，以确保它们在执行时能够满足ACID的事务特性。
                    // 事务是一种用于管理数据库操作的机制，它可以确保一组操作要么全部成功，要么全部失败，从而保证数据的一致性和完整性。
    public boolean add(User user) {
        if (StringUtils.isNotBlank(user.getPassword()))
            user.setPassword(passwordEncoder().encode(user.getPassword()));
        boolean inserts = this.save(user);
        userRoleService.saveBatch(user.getId(), user.getRoleIds());
        userApplicationService.saveBatch(user.getId(), user.getApplicationIds());
        return inserts;
    }

    @Override
    @Transactional
    @CacheInvalidate(name = "user::", key = "#id")  // @CacheInvalidate是JetCache提供的一个注解，用于标注在方法上，表示该方法执行后会清除指定的缓存。
                                                    // 它可以用来清除指定缓存的数据，以确保下次访问时能够重新加载最新的数据。
    public boolean delete(String id) {
        // 删除用户基本信息
        this.removeById(id);

        // 删除用户角色关联信息
        boolean removedRoles = userRoleService.removeByUserId(id);

        // 删除用户应用数据
        boolean removedApplications = userApplicationService.removeByUserId(id); // 假设这是对应的方法

        // 确保所有操作都成功执行
        return removedRoles && removedApplications;
    }

    @Override
    @Transactional
    @CacheInvalidate(name = "user::", key = "#user.id")
    public boolean update(User user) {
        if (StringUtils.isNotBlank(user.getPassword()))
            user.setPassword(passwordEncoder().encode(user.getPassword()));
        boolean isSuccess = this.updateById(user);
        userRoleService.saveBatch(user.getId(), user.getRoleIds());
        userApplicationService.saveBatch(user.getId(), user.getApplicationIds());
        return isSuccess;
    }

    @Override
    @Cached(name = "user::", key = "#id", cacheType = CacheType.BOTH)   // @Cached是JetCache提供的一个注解，用于标注在方法上，表示该方法的返回值会被缓存。
                                                                        // 它可以用来缓存方法的返回值，以提高方法的执行效率，减少对数据库或其他资源的访问。
    public UserVo get(String id) {
        User user = this.getById(id);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("user not found with id:" + id);
        }
        user.setRoleIds(userRoleService.queryByUserId(id));
        user.setApplicationIds(userApplicationService.queryByUserId(id));
        return new UserVo(user);
    }

    @Override
//    @Cached(name = "user::", key = "#uniqueId", cacheType = CacheType.BOTH)
    public User getByUniqueId(String uniqueId) {
        User user = this.getOne(new QueryWrapper<User>()
                .eq("username", uniqueId)
                .or()
                .eq("mobile", uniqueId));
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("user not found with uniqueId:" + uniqueId);
//            return user;
        }
        Set<String> roleIds = userRoleService.queryByUserId(user.getId());
        HashSet<String> roles = new HashSet<>();
        for (String roleId : roleIds) {
            roles.add(roleService.get(roleId).getCode());
        }

        user.setRoles(roles);
        user.setRoleIds(roleIds);
        user.setApplicationIds(userApplicationService.queryByUserId(user.getId()));
        return user;
    }

    @Override
    public IPage<UserVo> query(Page page, UserQueryParam userQueryParam) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 检查非空字段并将它们作为查询条件
        if (StringUtils.isNotBlank(userQueryParam.getName())) {
            queryWrapper.eq("name", userQueryParam.getName());
        }
        if (StringUtils.isNotBlank(userQueryParam.getUsername())) {
            queryWrapper.eq("username", userQueryParam.getUsername());
        }
        if (StringUtils.isNotBlank(userQueryParam.getMobile())) {
            queryWrapper.eq("mobile", userQueryParam.getMobile());
        }
        // 这里添加了usertype作为查询条件
        if (StringUtils.isNotBlank(userQueryParam.getUsertype())) {
            queryWrapper.eq("usertype", userQueryParam.getUsertype());
        }
        // 执行查询
        IPage<User> iPageUser = this.page(page, queryWrapper);
        // 将User实体转换为UserVo
        IPage<UserVo> iPageUserVo = iPageUser.convert(user -> {
            UserVo userVo = new UserVo(user);
            userVo.setRoleIds(userRoleService.queryByUserId(user.getId()));
            userVo.setApplicationIds(userApplicationService.queryByUserId(user.getId()));
            return userVo;
        });
        return iPageUserVo;
    }
}
