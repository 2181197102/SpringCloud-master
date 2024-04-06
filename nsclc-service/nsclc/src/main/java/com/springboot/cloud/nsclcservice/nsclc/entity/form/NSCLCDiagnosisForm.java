package com.springboot.cloud.nsclcservice.nsclc.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月09日 7:24 PM
 **/
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NSCLCDiagnosisForm extends BaseForm<NSCLCDiagnosis> {
    private String diagnosisCode;
    private String patientName;
    private String doctorName;
    private String imageFileLoc;
    private String maskFileLoc;
    private String featuresXlsxLoc;
    private int sysDiagResult;
    private int docDiagResult;
    private String diagDetails;
}
