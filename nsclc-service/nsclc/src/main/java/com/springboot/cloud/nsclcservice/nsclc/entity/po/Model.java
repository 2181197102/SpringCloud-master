package com.springboot.cloud.nsclcservice.nsclc.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.cloud.common.web.entity.po.BasePo;
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
@TableName("tb_nsclc_model")
public class Model extends BasePo {
    private String modelCode;
    private String fileName;
    private String modelName;
    private String modelLoc;
    private String modelDetails;
    private String deleted;
}
