package com.springboot.cloud.sysadmin.organization.entity.vo;

import com.springboot.cloud.common.web.entity.vo.BaseVo;
import com.springboot.cloud.sysadmin.organization.entity.po.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserVo extends BaseVo<User> {

    public UserVo(User user) { // 这个构造函数接受一个User对象作为参数，并将其属性拷贝到当前对象中。
        BeanUtils.copyProperties(user, this);
    }

    private String name;
    private String mobile;
    private String username;
    private String description;
    private String deleted;
    private Set<String> roleIds;
    private String createdBy;
    private String updatedBy;
    private Date createdTime;
    private Date updatedTime;

    // 新增字段
    private String attach; // 用户身份附件，与User实体中的定义相同，用于在视图层展示。
    private String usertype; // 用户身份类型，与User实体中的定义相同，用于在视图层展示。

    // 新增字段
    private Set<String> applicationIds; // 用户所属的应用ID集合，用于在视图层展示。
}
