package com.springboot.cloud.nsclcservice.nsclc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.util.UserContextHolder;
import com.springboot.cloud.nsclcservice.nsclc.config.ThriftConfig;
import com.springboot.cloud.nsclcservice.nsclc.dao.ModelMapper;
import com.springboot.cloud.nsclcservice.nsclc.entity.form.ModelForm;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.ModelQueryParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.param.PredictParam;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.Model;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import com.springboot.cloud.nsclcservice.nsclc.entity.vo.PredictVo;
import com.springboot.cloud.nsclcservice.nsclc.service.INsclcService;
import com.springboot.cloud.nsclcservice.nsclc.service.ImodelService;
import com.springboot.cloud.nsclcservice.nsclc.service.provider.RoleProvider;
import com.springboot.cloud.nsclcservice.nsclc.service.provider.entity.User;
import com.springboot.cloud.nsclcservice.nsclc.thrift.predict.PredictInfo;
import com.springboot.cloud.nsclcservice.nsclc.thrift.predict.PredictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月06日 2:47 PM
 **/
@Service
@Slf4j
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements ImodelService {

    @Resource
    private RoleProvider roleProvider;

    @Resource
    private INsclcService nsclcService;


    public PredictVo predict(PredictParam predictParam) throws TException {

        Model model = this.get(predictParam.getModelCode());
        String modelLoc = model.getModelLoc();
        TTransport transport = ThriftConfig.getTTransport();
        transport.open();
        TProtocol protocol = ThriftConfig.getTProtocol(transport);
        PredictService.Client client1 = new PredictService.Client(protocol);
        PredictInfo predictInfo = client1.predictNSCLC(predictParam.getImageFileLoc(), predictParam.getMaskFileLoc(), modelLoc);
        transport.close();
        int predictResult = predictInfo.predictResult;
        String featurePath = predictInfo.featurePath;
        PredictVo predictVo = new PredictVo(predictParam.getDiagnosisCode(), predictResult, featurePath);
        if (verifyUserRolePatient()) {
            insertPatient(predictParam, predictVo);
        }
        return predictVo;
    }

    private void insertPatient(PredictParam predictParam, PredictVo predictVo) {
        NSCLCDiagnosis save = new NSCLCDiagnosis();
        save.setDoctorName("自测");
        save.setModelCode(predictParam.getModelCode());
        save.setPatientName(getUserName());
        save.setSysDiagResult(predictVo.getSysDiagResult());
        save.setDocDiagResult(-1);
        save.setDiagDetails("自测");
        save.setMaskFileLoc(predictParam.getMaskFileLoc());
        save.setFeaturesXlsxLoc(predictVo.getFeaturesXlsxLoc());
        save.setImageFileLoc(predictParam.getImageFileLoc());
        save.setDiagnosisCode(predictVo.getDiagnosisCode());
        try {
            nsclcService.saveOneDiag(save);
        } catch (Exception e) {
            log.error("患者自测数据未保存成功，{}", e.getMessage());
        }
    }


    @Override
    public boolean saveModelInfo(ModelForm modelForm) {
        Model model = modelForm.toPo(Model.class);
        return this.save(model);
    }

    @Override
    public boolean delete(String modelCode) {
        LambdaQueryWrapper<Model> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Model::getModelCode, modelCode);
        return this.baseMapper.delete(lqw) == 1;
    }

    @Override
    public boolean update(Model model) {
        LambdaQueryWrapper<Model> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Model::getModelCode, model.getModelCode());
        return this.update(model, lqw);
    }

    @Override
    public Model get(String modelCode) {
        LambdaQueryWrapper<Model> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Model::getModelCode, modelCode);
        Model model = this.getOne(lqw);
        return model;
    }

    @Override
    public List<Model> getAll() {
        return this.list();
    }

    @Override
    public IPage<Model> query(Page page, ModelQueryParam toParam) {

        LambdaQueryWrapper<Model> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotBlank(toParam.getModelCode()), Model::getModelCode, toParam.getModelCode());
        lqw.like(StringUtils.isNotBlank(toParam.getModelName()), Model::getModelName, toParam.getModelName());
        lqw.like(StringUtils.isNotBlank(toParam.getFileName()), Model::getFileName, toParam.getFileName());
        return this.page(page, lqw);
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
