package com.springboot.cloud.sysadmin.organization.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.sysadmin.organization.entity.po.Application;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@ApiModel
@Data

public class ApplicationForm extends BaseForm<Application>{
    @ApiModelProperty(value = "应用名称")
    @NotBlank(message = "应用名称不能为空")
    @Length(min = 3, max = 20, message = "应用名称长度在3到20个字符")
    private String appName;

    @ApiModelProperty(value = "应用描述")
    private String description;

    @ApiModelProperty(value = "应用图标")
    private String appIcon;
}
