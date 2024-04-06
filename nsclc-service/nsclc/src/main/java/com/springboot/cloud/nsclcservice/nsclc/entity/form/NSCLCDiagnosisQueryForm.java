package com.springboot.cloud.nsclcservice.nsclc.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseQueryForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.NSCLCDiagnosisQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

@ApiModel
@Data

public class NSCLCDiagnosisQueryForm extends BaseQueryForm<NSCLCDiagnosisQueryParam>{

    private String diagnosisCode;
    private String patientName;
    private String doctorName;
    private int sysDiagResult;
    private int docDiagResult;
    private String modelCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    @ApiModelProperty(value = "查询开始时间")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    @ApiModelProperty(value = "查询结束时间")
    private Date createdTimeEnd;
}
