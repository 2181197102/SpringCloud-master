package com.springboot.cloud.nailservice.nail.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PredictVo {

        private String diagnosisCode;

        private String diagResult;

        private String openId;

        private String patientName;

        private String userType;

}
