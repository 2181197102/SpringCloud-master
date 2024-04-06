package com.springboot.cloud.nsclcservice.nsclc.entity.vo;

import com.springboot.cloud.common.web.entity.vo.BaseVo;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月09日 9:00 PM
 **/
@Data
@NoArgsConstructor
public class NSCLCDiagnosisVo extends BaseVo<NSCLCDiagnosis> {
    public NSCLCDiagnosisVo(NSCLCDiagnosis nsclcDiagnosis) {
        BeanUtils.copyProperties(nsclcDiagnosis, this);
    }

    private String diagnosisCode;
    private String patientName;
    private String doctorName;
    private String imageFileLoc;
    private String maskFileLoc;
    private String featuresXlsxLoc;
    private int sysDiagResult;
    private int docDiagResult;
    private String diagDetails;

    private String modelName;
}
