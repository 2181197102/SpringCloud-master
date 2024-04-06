package com.springboot.cloud.nsclcservice.nsclc.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data

public class NSCLCDiagnosisUpdateForm extends BaseForm<NSCLCDiagnosis>{
    private String id;
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
