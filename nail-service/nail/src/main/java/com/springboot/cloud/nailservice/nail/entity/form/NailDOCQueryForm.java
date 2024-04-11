package com.springboot.cloud.nailservice.nail.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseQueryForm;
import com.springboot.cloud.nailservice.nail.entity.param.NailDOCQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

@ApiModel
@Data
public class NailDOCQueryForm extends BaseQueryForm<NailDOCQueryParam> {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "应用ID")
    private String applicationId;

    @ApiModelProperty(value = "诊断结果准确率，0：准确, 1：基本准确, 2：不准确")
    private Integer resultAccuracy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    @ApiModelProperty(value = "查询开始时间")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    @ApiModelProperty(value = "查询结束时间")
    private Date createdTimeEnd;

}
