package com.springboot.cloud.nailservice.nail.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.param.PredictParam;
import com.springboot.cloud.nailservice.nail.entity.vo.PredictVo;
import com.springboot.cloud.nailservice.nail.service.IModelService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/nail/model")
@Api("nail/model")
@Slf4j
public class ModelController {

    private final IModelService modelService;

    @Autowired
    public ModelController(IModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/predict")
    // TODO 入参不合理 不可以输入带引号的code,直接输入code才ok
    public Result<PredictVo> predict(@RequestBody PredictParam predictParam) throws IOException {
        return Result.success(modelService.predict(predictParam));
    }
}
