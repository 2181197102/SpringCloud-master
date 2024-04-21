package com.springboot.cloud.nailservice.nail.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.form.NailDiagQueryForm;
import com.springboot.cloud.nailservice.nail.entity.form.NailDiagUpdateForm;
import com.springboot.cloud.nailservice.nail.entity.form.SaveDiagForm;
import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import com.springboot.cloud.nailservice.nail.entity.param.UploadImageParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.entity.vo.UploadImageVo;
import com.springboot.cloud.nailservice.nail.service.IFileService;
import com.springboot.cloud.nailservice.nail.service.INailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/nail")
@Api("nail")
@Slf4j
public class NailController {
    @Resource   // @Resource是J2EE提供的注解，由J2EE提供，而@Autowired是Spring提供的注解。
                // @Resource默认按照名称进行装配，如果找不到与名称匹配的bean，则按照类型进行装配。
                // @Resource有两个属性是比较重要的，分别是name和type，Spring将name属性解析为bean的名字，type属性解析为bean的类型。
    private INailService nailService;

    @Resource
    private IFileService fileService;

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping("/file/upload/image")
    public Result uploadFileImage(UploadImageParam uploadImageParam) {
        UploadImageVo uploadImageVo = fileService.saveImage(uploadImageParam);
        return Result.success(uploadImageVo);
    }

    @ApiOperation(value = "保存诊断", notes = "保存诊断")
    @PostMapping("/save/diag")
    public Result saveDiag(@RequestBody SaveDiagForm saveDiagForm) {
        boolean save = nailService.saveDiag(saveDiagForm);
        return Result.success(save);
    }

    @ApiOperation(value = "删除诊断", notes = "根据诊断编码删除诊断")
    @DeleteMapping(value = "/{diagnosisCode}")
    public Result delete(@PathVariable String diagnosisCode) {
        return Result.success(nailService.delete(diagnosisCode));
    }

    @ApiOperation(value = "更新诊断", notes = "根据诊断编码更新诊断")
    @PutMapping(value = "/{diagnosisCode}")
    public Result update(@PathVariable String diagnosisCode, @RequestBody NailDiagUpdateForm nailDiagUpdateForm) {
        NailDiag nailDiag = nailDiagUpdateForm.toPo(diagnosisCode, NailDiag.class);
        return Result.success(nailService.update(nailDiag));
    }

    @ApiOperation(value = "获取诊断", notes = "根据诊断编码获取诊断")
    @GetMapping(value = "/{diagnosisCode}")
    public Result get(@PathVariable String diagnosisCode) {
        log.debug("get with diagnosisCode:{}", diagnosisCode);
        return Result.success(nailService.get(diagnosisCode));
    }

    @ApiOperation(value = "获取所有诊断", notes = "获取所有诊断")
    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(nailService.getAll());
    }

    @ApiOperation(value = "查询诊断", notes = "根据查询条件<诊断编码，诊断者姓名，患者姓名，诊断结果准确率>查询诊断")
    @PostMapping(value = "/conditions")
    public Result query(@RequestBody NailDiagQueryForm nailDiagQueryForm) {
        log.debug("query with conditions:{}", nailDiagQueryForm);
        return Result.success(nailService.query(nailDiagQueryForm.getPage(), nailDiagQueryForm.toParam(NailDiagQueryParam.class)));
    }


}
