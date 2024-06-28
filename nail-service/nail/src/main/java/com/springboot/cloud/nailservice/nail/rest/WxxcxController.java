package com.springboot.cloud.nailservice.nail.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.entity.form.NailDiagQueryForm;
import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import com.springboot.cloud.nailservice.nail.entity.param.RegisterParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.service.IWxxcxService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/wxxcx")
@Api("wxxcx")
@Slf4j
public class WxxcxController {

    @Resource
    private  IWxxcxService wxxcxService;


    // 其他方法

    @ApiOperation(value = "诊断模糊查询", notes = "根据查询条件<诊断编码，诊断者姓名，患者姓名，诊断结果准确率>查询诊断，固定openid，确保用户查到的是当前用户所拥有的诊断信息")
    @PostMapping("/fuzzy-query")
    public Result<IPage<NailDiag>> fuzzyQuery(@RequestBody NailDiagQueryForm nailDiagQueryForm) {
        log.debug("query with conditions:{}", nailDiagQueryForm);
        Page<NailDiag> page = nailDiagQueryForm.getPage();

        // 设置分页参数的默认值
        if (page.getCurrent() == 0) {
            page.setCurrent(1); // 默认当前页为1
        }
        if (page.getSize() == 0) {
            page.setSize(30); // 默认每页显示100条记录
        }
        return Result.success(wxxcxService.fuzzyQuery(page, nailDiagQueryForm.toParam(NailDiagQueryParam.class)));
    }

    @ApiOperation(value = "用户注册", notes = "根据提供的注册信息注册一个新用户")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody RegisterParam registerParam) {
        log.debug("Registering user with information: {}", registerParam);

        boolean isRegistered = wxxcxService.register(registerParam);

        if (isRegistered) {
            return Result.success("User registered successfully");
        } else {
            return Result.fail("User registration failed");
        }
    }




}