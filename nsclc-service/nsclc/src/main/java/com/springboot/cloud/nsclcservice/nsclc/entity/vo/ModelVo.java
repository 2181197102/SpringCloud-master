package com.springboot.cloud.nsclcservice.nsclc.entity.vo;

import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月12日 3:17 PM
 **/
@Data
@NoArgsConstructor
public class ModelVo {
    public ModelVo(Model model) {
        BeanUtils.copyProperties(model, this);
    }
    private String modelCode;
    private String fileName;
    private String modelName;
    private String modelLoc;
    private String modelDetails;
}
