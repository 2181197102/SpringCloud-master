package com.springboot.cloud.nsclcservice.nsclc.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data

public class ModelUpdateForm extends BaseForm<Model>{
    private String modelCode;
    private String fileName;
    private String modelName;
    private String modelLoc;
    private String modelDetails;
}
