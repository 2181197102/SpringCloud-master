package com.springboot.cloud.nsclcservice.nsclc.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.NSCLCDiagnosisQueryForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.NSCLCDiagnosisUpdateForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.SaveDiagForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.NSCLCDiagnosisQueryParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.PredictParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadImageParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.UploadMaskParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadImageVo;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.UploadMaskVo;
import com.springboot.cloud.nsclcservice.nsclc.service.IFileService;
import com.springboot.cloud.nsclcservice.nsclc.service.INsclcService;
import com.springboot.cloud.nsclcservice.nsclc.service.ImodelService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月05日 3:22 PM
 **/
@RestController
@RequestMapping("/nsclc")
@Api("nsclc")
@Slf4j
public class NsclcController {
    @Resource
    private ImodelService modelService;

    @Resource
    private IFileService fileService;

    @Resource
    private INsclcService nsclcService;

    @PostMapping("/predict")
    public Result predict(PredictParam predictParam) throws TException, IOException {
        return Result.success(modelService.predict(predictParam));
    }

    @PostMapping("/file/upload/image")
    public Result uploadFileImage(UploadImageParam uploadImageParam) {
        UploadImageVo uploadImageVo = fileService.saveImage(uploadImageParam);
        return Result.success(uploadImageVo);
    }

    @PostMapping("/file/upload/mask")
    public Result uploadFileMask(UploadMaskParam uploadMaskParam) {
        UploadMaskVo uploadMaskVo = fileService.saveMask(uploadMaskParam);
        return Result.success(uploadMaskVo);
    }

    @PostMapping("/save/diag")
    public Result saveDiag(@RequestBody SaveDiagForm saveDiagForm) {
        boolean save = nsclcService.saveDiag(saveDiagForm);
        return Result.success(save);
    }

    @DeleteMapping(value = "/{diagnosisCode}")
    public Result delete(@PathVariable String diagnosisCode) {
        return Result.success(nsclcService.delete(diagnosisCode));
    }

    @PutMapping(value = "/{diagnosisCode}")
    public Result update(@PathVariable String diagnosisCode, @RequestBody NSCLCDiagnosisUpdateForm nsclcDiagnosisUpdateForm) {
        NSCLCDiagnosis nsclcDiagnosis = nsclcDiagnosisUpdateForm.toPo(diagnosisCode, NSCLCDiagnosis.class);
        return Result.success(nsclcService.update(nsclcDiagnosis));
    }

    @GetMapping(value = "/{diagnosisCode}")
    public Result get(@PathVariable String diagnosisCode) {
        log.debug("get with diagnosisCode:{}", diagnosisCode);
        return Result.success(nsclcService.get(diagnosisCode));
    }

    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(nsclcService.getAll());
    }


    @PostMapping(value = "/conditions")
    public Result query(@RequestBody NSCLCDiagnosisQueryForm nsclcDiagnosisQueryForm) {
        log.debug("query with conditions:{}", nsclcDiagnosisQueryForm);
        return Result.success(nsclcService.query(nsclcDiagnosisQueryForm.getPage(), nsclcDiagnosisQueryForm.toParam(NSCLCDiagnosisQueryParam.class)));
    }

}
