package com.springboot.cloud.nailservice.nail.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseQueryForm;
import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

@ApiModel
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NailDiagQueryForm extends BaseQueryForm<NailDiagQueryParam> {

    @ApiModelProperty(value = "诊断编码")
    private String diagnosisCode;

    @ApiModelProperty(value = "诊断者姓名")
    private String doctorName;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "诊断结果准确率，1：准确, 0：不准确")
    private Integer resultAccuracy;

    @ApiModelProperty(value = "微信小程序用户的openid")
    private String openId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    @ApiModelProperty(value = "查询开始时间")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    @ApiModelProperty(value = "查询结束时间")
    private Date createdTimeEnd;

}
