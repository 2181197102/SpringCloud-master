package com.springboot.cloud.nailservice.nail.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.nailservice.nail.entity.po.Nail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class NailPATForm extends BaseForm<Nail> {
    @ApiModelProperty(value = "用户ID")
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @ApiModelProperty(value = "应用ID")
    @NotBlank(message = "应用ID不能为空")
    private String applicationId;

    @ApiModelProperty(value = "用户上传图片，json存储图片url")
    @NotBlank(message = "图片URL不能为空")
    private String imageFile;

    @ApiModelProperty(value = "诊断结果")
    private String diagResult;

    @ApiModelProperty(value = "诊断结果准确率，0：准确, 1：基本准确, 2：不准确")
    private Integer resultAccuracy;

    @ApiModelProperty(value = "结果反馈")
    private String feedback;
}
