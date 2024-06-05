package com.springboot.cloud.nailservice.nail.entity.param;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PredictParam {

    private String diagnosisCode;

    private String openId;

    private String patientName;

}
