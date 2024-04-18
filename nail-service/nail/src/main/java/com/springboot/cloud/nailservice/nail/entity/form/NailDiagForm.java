package com.springboot.cloud.nailservice.nail.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel // Swagger注解，用于API文档中显示该类的信息
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NailDiagForm extends BaseForm<NailDiag> {
    @ApiModelProperty(value = "诊断编码")
    private String diagnosisCode;

    @ApiModelProperty(value = "诊断者姓名")
    private String doctorName;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "用户上传图片，存储图片url")
    private String imageFile;

    @ApiModelProperty(value = "诊断结果")
    private String diagResult;

    @ApiModelProperty(value = "诊断结果准确率，1：准确, 0：不准确")
    private Integer resultAccuracy;

    @ApiModelProperty(value = "结果反馈")
    private String feedback;
}
