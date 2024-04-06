package com.springboot.cloud.nsclcservice.nsclc.entity.param;

import com.springboot.cloud.common.web.entity.param.BaseParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NSCLCDiagnosisQueryParam extends BaseParam<NSCLCDiagnosis> {
    private String diagnosisCode;
    private String patientName;
    private String doctorName;
    private Integer sysDiagResult;
    private Integer docDiagResult;
    private String modelCode;
}
