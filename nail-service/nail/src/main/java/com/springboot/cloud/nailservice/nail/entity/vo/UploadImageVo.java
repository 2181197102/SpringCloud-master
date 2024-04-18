package com.springboot.cloud.nailservice.nail.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadImageVo {

    private String diagnosisCode;
    private String imageFile;

}
