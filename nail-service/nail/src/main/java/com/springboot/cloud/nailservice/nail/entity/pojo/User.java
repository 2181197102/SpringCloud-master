package com.springboot.cloud.nailservice.nail.entity.pojo;

import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false )
@NoArgsConstructor
public class User extends BasePo {
    private String name;
    private String mobile;
    private String username;
    private String password;
    private String attach;
    private String usertype;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
}