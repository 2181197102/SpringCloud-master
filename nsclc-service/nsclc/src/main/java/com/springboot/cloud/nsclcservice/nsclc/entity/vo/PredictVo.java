package com.springboot.cloud.nsclcservice.nsclc.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月09日 12:50 PM
 **/
@Data
@AllArgsConstructor
public class PredictVo {
    private String diagnosisCode;
    private int sysDiagResult;
    private String featuresXlsxLoc;
}
