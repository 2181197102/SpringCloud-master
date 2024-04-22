package com.springboot.cloud.nsclcservice.nsclc.entity.param;

import com.springboot.cloud.common.web.entity.param.BaseParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelQueryParam extends BaseParam<Model> {
    private String modelCode;
    private String fileName;
    private String modelName;
}
