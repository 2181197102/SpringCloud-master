package com.springboot.cloud.sysadmin.organization.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.sysadmin.organization.entity.form.ApplicationForm;
import com.springboot.cloud.sysadmin.organization.entity.form.ApplicationQueryForm;
import com.springboot.cloud.sysadmin.organization.entity.form.ApplicationUpdateForm;
import com.springboot.cloud.sysadmin.organization.entity.param.ApplicationQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.Application;
import com.springboot.cloud.sysadmin.organization.service.IApplicationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app")
@Api("app")
@Slf4j

public class ApplicationController {

        @Autowired
        private IApplicationService applicationService;

        @ApiOperation(value = "新增应用", notes = "新增一个应用")
        @ApiImplicitParam(name = "applicationForm", value = "新增应用form表单", required = true, dataType = "ApplicationForm")
        @PostMapping
        public Result add(@Valid @RequestBody ApplicationForm applicationForm) {
            log.debug("name:{}", applicationForm);
            Application application = applicationForm.toPo(Application.class);
            return Result.success(applicationService.add(application));
        }

        @ApiOperation(value = "删除应用", notes = "根据应用id删除应用")
        @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "String", paramType = "path")
        @DeleteMapping(value = "/{id}")
        public Result delete(@PathVariable String id) {
            return Result.success(applicationService.delete(id));
        }

        @ApiOperation(value = "修改应用", notes = "修改应用信息")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "String", paramType = "path"),
                @ApiImplicitParam(name = "applicationUpdateForm", value = "修改应用form表单", required = true, dataType = "ApplicationUpdateForm")
        })
        @PutMapping(value = "/{id}")
        public Result update(@PathVariable String id, @Valid @RequestBody ApplicationUpdateForm applicationUpdateForm) {
            Application application = applicationUpdateForm.toPo(id, Application.class);
            return Result.success(applicationService.update(application));
        }

        @ApiOperation(value = "获取应用", notes = "根据应用id获取应用")
        @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "String", paramType = "path")
        @GetMapping(value = "/{id}")
        public Result get(@PathVariable String id) {
            log.debug("get with id:{}", id);
            return Result.success(applicationService.get(id));
        }

        @ApiOperation(value = "获取所有应用", notes = "获取所有应用")
        @GetMapping(value = "/all")
        public Result get() {
            return Result.success(applicationService.getAll());
        }

        @ApiOperation(value = "查询应用", notes = "根据参数查询应用")
        @ApiImplicitParam(name = "applicationQueryForm", value = "查询参数", required = true, dataType = "ApplicationQueryForm")
        @ApiResponses(
                @ApiResponse(code = 200, message = "处理成功", response = Result.class)
        )
        @PostMapping(value = "/conditions")
        public Result query(@Valid @RequestBody ApplicationQueryForm applicationQueryForm) {
            log.debug("query with appName:{}", applicationQueryForm);
            return Result.success(applicationService.query(applicationQueryForm.getPage(), applicationQueryForm.toParam(ApplicationQueryParam.class)));
        }

}
