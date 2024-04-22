package com.springboot.cloud.nailservice.nail.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UploadImageVo {

    private String diagnosisCode;
    private String imageFile;

}
