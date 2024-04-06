package com.springboot.cloud.nsclcservice.nsclc.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月08日 8:22 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_nsclc_diag")
public class NSCLCDiagnosis extends BasePo {
    private String diagnosisCode;
    private String patientName;
    private String doctorName;
    private String imageFileLoc;
    private String maskFileLoc;
    private String modelCode;
    private String featuresXlsxLoc;
    private int sysDiagResult;
    private int docDiagResult;
    private String diagDetails;


    @TableField(exist = false)
    private String modelName;
}
