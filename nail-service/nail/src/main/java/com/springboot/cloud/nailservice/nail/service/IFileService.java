package com.springboot.cloud.nailservice.nail.service;

import com.springboot.cloud.nailservice.nail.entity.param.UploadImageParam;
import com.springboot.cloud.nailservice.nail.entity.vo.UploadImageVo;

public interface IFileService {

    UploadImageVo saveImage(UploadImageParam uploadImageParam, String openId);

}
