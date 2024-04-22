package com.springboot.cloud.nsclcservice.nsclc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.util.UserContextHolder;
import com.springboot.cloud.nsclcservice.nsclc.dao.NSCLCDiagnosisMapper;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.SaveDiagForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.NSCLCDiagnosisQueryParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.NSCLCDiagnosisVo;
import com.springboot.cloud.nsclcservice.nsclc.service.INsclcService;
import com.springboot.cloud.nsclcservice.nsclc.service.ImodelService;
import com.springboot.cloud.nsclcservice.nsclc.service.provider.RoleProvider;
import com.springboot.cloud.nsclcservice.nsclc.service.provider.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月05日 3:21 PM
 **/
@Service
@Slf4j
public class NsclcServiceImpl extends ServiceImpl<NSCLCDiagnosisMapper, NSCLCDiagnosis> implements INsclcService {

    @Resource
    private ImodelService modelService;

    @Resource
    private RoleProvider roleProvider;

    @Override
    public boolean saveDiag(SaveDiagForm saveDiagForm) {
        String username = getUserName();
        saveDiagForm.setDoctorName(username);
        NSCLCDiagnosis diagnosis = saveDiagForm.toPo(NSCLCDiagnosis.class);
        return this.save(diagnosis);
    }

    @Override
    public boolean delete(String diagnosisCode) {
        LambdaQueryWrapper<NSCLCDiagnosis> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NSCLCDiagnosis::getDiagnosisCode, diagnosisCode);
        return this.baseMapper.delete(lqw) == 1;
    }

    @Override
    public boolean update(NSCLCDiagnosis nsclcDiagnosis) {
        LambdaQueryWrapper<NSCLCDiagnosis> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NSCLCDiagnosis::getDiagnosisCode, nsclcDiagnosis.getDiagnosisCode());
        return this.update(nsclcDiagnosis, lqw);
    }

    @Override
    public NSCLCDiagnosisVo get(String diagnosisCode) {
        LambdaQueryWrapper<NSCLCDiagnosis> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NSCLCDiagnosis::getDiagnosisCode, diagnosisCode);
        NSCLCDiagnosis nsclcDiagnosis = this.getOne(lqw);
        String modelName = modelService.get(nsclcDiagnosis.getModelCode()).getModelName();
        nsclcDiagnosis.setModelName(modelName);
        return new NSCLCDiagnosisVo(nsclcDiagnosis);
    }

    @Override
    public List<NSCLCDiagnosis> getAll() {
        List<Model> models = modelService.getAll();
        Map<String, String> modelCodeNameMap = models.stream().collect(Collectors.toMap(Model::getModelCode, Model::getModelName));
        List<NSCLCDiagnosis> diagnoses = this.list();

        for (NSCLCDiagnosis diagnosis : diagnoses) {
            diagnosis.setModelName(modelCodeNameMap.get(diagnosis.getModelCode()));
        }

        return diagnoses;
    }

    @Override
    public IPage<NSCLCDiagnosis> query(Page page, NSCLCDiagnosisQueryParam nsclcDiagnosisQueryParam) {
        boolean isPatient = verifyUserRolePatient();
        List<Model> models = modelService.getAll();
        Map<String, String> modelCodeNameMap = models.stream().collect(Collectors.toMap(Model::getModelCode, Model::getModelName));
        LambdaQueryWrapper<NSCLCDiagnosis> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotBlank(nsclcDiagnosisQueryParam.getDiagnosisCode()), NSCLCDiagnosis::getDiagnosisCode, nsclcDiagnosisQueryParam.getDiagnosisCode());
        if (isPatient) {
            lqw.like(NSCLCDiagnosis::getPatientName, getUserName());
        } else {
            lqw.like(StringUtils.isNotBlank(nsclcDiagnosisQueryParam.getPatientName()), NSCLCDiagnosis::getPatientName, nsclcDiagnosisQueryParam.getPatientName());
        }
        lqw.like(StringUtils.isNotBlank(nsclcDiagnosisQueryParam.getDoctorName()), NSCLCDiagnosis::getDoctorName, nsclcDiagnosisQueryParam.getDoctorName());
        lqw.eq(StringUtils.isNotBlank(nsclcDiagnosisQueryParam.getModelCode()), NSCLCDiagnosis::getModelCode, nsclcDiagnosisQueryParam.getModelCode());
        lqw.eq(verify(nsclcDiagnosisQueryParam.getDocDiagResult()), NSCLCDiagnosis::getDocDiagResult, nsclcDiagnosisQueryParam.getDocDiagResult());
        lqw.eq(verify(nsclcDiagnosisQueryParam.getSysDiagResult()), NSCLCDiagnosis::getSysDiagResult, nsclcDiagnosisQueryParam.getSysDiagResult());
        IPage<NSCLCDiagnosis> retPage = this.page(page, lqw);
        List<NSCLCDiagnosis> records = retPage.getRecords();
        for (NSCLCDiagnosis record : records) {
            record.setModelName(modelCodeNameMap.get(record.getModelCode()));
        }
        return retPage;
    }

    @Override
    public void saveOneDiag(NSCLCDiagnosis save) {
        this.save(save);
    }

    private boolean verify(Integer result) {
        return result != null && result != -1;
    }

    private boolean verifyUserRolePatient() {
        String username = getUserName();
        Result<User> user = roleProvider.queryRolesByUserId(username);
        Set<String> roles = user.getData().getRoles();
        for (String role : roles) {
            if ("PAT".equals(role)) {
                return true;
            }
        }
        return false;
    }

    private String getUserName() {
        return UserContextHolder.getInstance().getUsername();
    }
}
