package com.springboot.cloud.nsclcservice.nsclc.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.ModelForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.ModelQueryForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.ModelUpdateForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.ModelQueryParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadModelParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.ModelVo;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadModelVo;
import com.springboot.cloud.nsclcservice.nsclc.service.IFileService;
import com.springboot.cloud.nsclcservice.nsclc.service.ImodelService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月12日 3:02 PM
 **/
@RestController
@RequestMapping("/nsclc/model")
@Api("nsclc/model")
@Slf4j
public class ModelController {


    @Resource
    private ImodelService modelService;

    @Resource
    private IFileService fileService;

    @PostMapping("/file/upload/model")
    public Result uploadModel(UploadModelParam uploadModelParam) {
        UploadModelVo uploadModelVo = fileService.saveModel(uploadModelParam);
        return Result.success(uploadModelVo);
    }


    @PostMapping("/save")
    public Result saveModelInfo(@RequestBody ModelForm modelForm) {
        boolean save = modelService.saveModelInfo(modelForm);
        return Result.success(save);
    }

    @DeleteMapping(value = "/{modelCode}")
    public Result delete(@PathVariable String modelCode) {
        return Result.success(modelService.delete(modelCode));
    }

    @PutMapping(value = "/{modelCode}")
    public Result update(@PathVariable String modelCode, @RequestBody ModelUpdateForm modelUpdateForm) {
        Model model = modelUpdateForm.toPo(modelCode, Model.class);
        return Result.success(modelService.update(model));
    }

    @GetMapping(value = "/{modelCode}")
    public Result get(@PathVariable String modelCode) {
        log.debug("get with modelCode:{}", modelCode);
        return Result.success(new ModelVo(modelService.get(modelCode)));
    }

    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(modelService.getAll());
    }


    @PostMapping(value = "/conditions")
    public Result query(@RequestBody ModelQueryForm modelQueryForm) {
        log.debug("query with conditions:{}", modelQueryForm);
        return Result.success(modelService.query(modelQueryForm.getPage(), modelQueryForm.toParam(ModelQueryParam.class)));
    }
}
