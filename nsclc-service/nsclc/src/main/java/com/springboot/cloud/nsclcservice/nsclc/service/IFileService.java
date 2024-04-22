package com.springboot.cloud.nsclcservice.nsclc.service;

import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadImageParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadMaskParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadModelParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadImageVo;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadMaskVo;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadModelVo;

public interface IFileService {

    UploadImageVo saveImage(UploadImageParam uploadImageParam);
    UploadMaskVo saveMask(UploadMaskParam uploadMaskParam);

    UploadModelVo saveModel(UploadModelParam uploadModelParam);

}
