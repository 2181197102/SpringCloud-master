package com.springboot.cloud.nailservice.nail.service;

import com.springboot.cloud.nailservice.nail.entity.param.PredictParam;
import com.springboot.cloud.nailservice.nail.entity.vo.PredictVo;

import java.io.IOException;

public interface IModelService {
    PredictVo predict(PredictParam predictParam) throws IOException;
}
