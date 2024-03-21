package com.springboot.cloud.sysadmin.organization.entity.vo;

import com.springboot.cloud.common.web.entity.vo.BaseVo;
import com.springboot.cloud.sysadmin.organization.entity.po.Application;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor

public class ApplicationVo extends BaseVo<Application> {

    public ApplicationVo(Application application) {
        BeanUtils.copyProperties(application, this);
    }


    private String id;
    private String appName;
    private String description;
    private String appIcon;

    // Getters and Setters
}
