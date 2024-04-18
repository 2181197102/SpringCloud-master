package com.springboot.cloud.nailservice.nail.service.impl;


//TODO 上传接口（提示上传成功信息；返回结果【实时】或'''等待返回【延迟】'''），返回诊断结果接口，查询诊断信息接口

//TODO 调用外部http请求使用模型接口

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.util.UserContextHolder;
import com.springboot.cloud.nailservice.nail.clients.UserClient;
import com.springboot.cloud.nailservice.nail.dao.NailDiagMapper;
import com.springboot.cloud.nailservice.nail.entity.form.SaveDiagForm;
import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import com.springboot.cloud.nailservice.nail.entity.vo.NailDiagVo;
import com.springboot.cloud.nailservice.nail.service.INailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service    // @Service是Spring框架提供的一个注解，用于标注在类上，表示将该类定义为Spring容器中的一个服务组件（Service Component）。
            // 它是一个专门用于业务逻辑层（Business Service Layer）的注解，表明该类主要用于执行业务操作、事务处理等。
@Slf4j      // @Slf4j是Lombok提供的一个注解，用于在类上生成一个log属性，用于记录日志。
public class NailService extends ServiceImpl<NailDiagMapper, NailDiag> implements INailService {

    @Resource
    private UserClient userClient;

    @Override
    public boolean saveDiag(SaveDiagForm saveDiagForm) {
        String username = getUserName();
        saveDiagForm.setDoctorName(username);
        NailDiag diagnosis = saveDiagForm.toPo(NailDiag.class);
        return this.save(diagnosis);
    }

    @Override
    public boolean delete(String diagnosisCode) {
        LambdaQueryWrapper<NailDiag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NailDiag::getDiagnosisCode, diagnosisCode);
        return this.baseMapper.delete(lqw) == 1;
    }

    @Override
    public boolean update(NailDiag nailDiag) {
        LambdaQueryWrapper<NailDiag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NailDiag::getDiagnosisCode, nailDiag.getDiagnosisCode());
        return this.update(nailDiag, lqw);
    }

    @Override
    public NailDiagVo get(String diagnosisCode) {
        LambdaQueryWrapper<NailDiag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(NailDiag::getDiagnosisCode, diagnosisCode);
        NailDiag nailDiag = this.getOne(lqw);
        return new NailDiagVo(nailDiag);
    }

    @Override
    public List<NailDiag> getAll() {
        List<NailDiag> diagnoses = this.list();
        return diagnoses;
    }

    @Override
    public IPage<NailDiag> query(Page page, NailDiagQueryParam nailDiagQueryParam) {
        boolean isPatient = verifyUserRolePatient();
        LambdaQueryWrapper<NailDiag> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotBlank(nailDiagQueryParam.getDiagnosisCode()), NailDiag::getDiagnosisCode, nailDiagQueryParam.getDiagnosisCode());
        if (isPatient) {
            lqw.like(NailDiag::getPatientName, getUserName());
        } else {
            lqw.like(StringUtils.isNotBlank(nailDiagQueryParam.getPatientName()), NailDiag::getPatientName, nailDiagQueryParam.getPatientName());
        }
        lqw.like(StringUtils.isNotBlank(nailDiagQueryParam.getDoctorName()), NailDiag::getDoctorName, nailDiagQueryParam.getDoctorName());
        lqw.eq(verify(nailDiagQueryParam.getResultAccuracy()), NailDiag::getResultAccuracy, nailDiagQueryParam.getResultAccuracy());
        IPage<NailDiag> retPage = this.page(page, lqw);
        return retPage;
    }

    @Override
    public void saveOneDiag(NailDiag save) {
        this.save(save);
    }

    private boolean verify(Integer result) {
        return result != null && result != -1;
    }

    private boolean verifyUserRolePatient() {
        String username = getUserName();
        Result<User> user = userClient.getUserByUniqueId(username);
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

/*// 在业务逻辑中根据用户角色选择VO进行展示
public class NailService {

    public Object getNailData(String userId, String userRole) {
        // 根据userId获取Nail数据，这里假设获取到了nail对象

        if ("patient".equals(userRole)) {
            // 如果是患者角色，返回患者展示的VO
            return convertToPatientVo(nail);
        } else if ("medical_staff".equals(userRole)) {
            // 如果是医护人员角色，返回医护人员展示的VO
            return convertToMedicalStaffVo(nail);
        } else {
            // 其他情况，根据业务需求处理
            return null;
        }
    }

    private PatientNailVo convertToPatientVo(Nail nail) {
        PatientNailVo patientVo = new PatientNailVo();
        // 填充患者展示VO的数据，这里假设只需要填充基本数据
        // patientVo.setXXX(nail.getXXX());
        return patientVo;
    }

    private MedicalStaffNailVo convertToMedicalStaffVo(Nail nail) {
        MedicalStaffNailVo medicalStaffVo = new MedicalStaffNailVo();
        // 填充医护人员展示VO的数据，包括患者展示VO的数据以及医护人员需要的额外数据
        // medicalStaffVo.setXXX(nail.getXXX());
        // medicalStaffVo.setYYY(nail.getYYY());
        // medicalStaffVo.setZZZ(nail.getZZZ());
        return medicalStaffVo;
    }
}
*/