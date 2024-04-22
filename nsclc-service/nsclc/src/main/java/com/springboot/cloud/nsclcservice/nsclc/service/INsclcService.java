package com.springboot.cloud.nsclcservice.nsclc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.SaveDiagForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.NSCLCDiagnosisQueryParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.NSCLCDiagnosisVo;

import java.util.List;

public interface INsclcService {
    boolean saveDiag(SaveDiagForm saveDiagForm);

    boolean delete(String diagnosisCode);

    boolean update(NSCLCDiagnosis nsclcDiagnosis);

    NSCLCDiagnosisVo get(String diagnosisCode);

    List<NSCLCDiagnosis> getAll();

    IPage<NSCLCDiagnosis> query(Page page, NSCLCDiagnosisQueryParam toParam);

    void saveOneDiag(NSCLCDiagnosis save);
}
