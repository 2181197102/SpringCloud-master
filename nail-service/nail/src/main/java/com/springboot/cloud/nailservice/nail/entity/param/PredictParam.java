package com.springboot.cloud.nailservice.nail.entity.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PredictParam {

    private String diagnosisCode;

    private String imageFile;

}
