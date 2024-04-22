package com.springboot.cloud.nsclcservice.nsclc.entity.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月09日 12:50 PM
 **/
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PredictParam {
    private String diagnosisCode;
    private String imageFileLoc;
    private String maskFileLoc;
    private String modelCode;
}
