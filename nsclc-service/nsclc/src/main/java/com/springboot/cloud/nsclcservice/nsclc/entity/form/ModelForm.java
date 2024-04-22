package com.springboot.cloud.nsclcservice.nsclc.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月12日 2:49 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelForm extends BaseForm<Model> {
    private String modelCode;
    private String fileName;
    private String modelName;
    private String modelLoc;
    private String modelDetails;
}
