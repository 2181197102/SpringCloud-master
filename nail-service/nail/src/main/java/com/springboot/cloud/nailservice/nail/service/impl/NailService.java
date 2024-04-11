package com.springboot.cloud.nailservice.nail.service.impl;


//TODO 上传接口（提示上传成功信息；返回结果【实时】或'''等待返回【延迟】'''），返回诊断结果接口，查询诊断信息接口

//TODO 调用外部http请求使用模型接口

import jdk.jshell.Diag;
import org.springframework.beans.factory.annotation.Autowired;

public class NailService {
    private final DiagRepository diagRepository;

    @Autowired
    public DiagServiceImpl(DiagRepository diagRepository) {
        this.diagRepository = diagRepository;
    }

    @Override
    public Diag getDiagById(String id) {
        return diagRepository.findById(id).orElse(null);
    }

    @Override
    public List<Diag> getDiagsByUserId(String userId) {
        return diagRepository.findByUserId(userId);
    }

    @Override
    public Diag createDiag(Diag diag) {
        return diagRepository.save(diag);
    }

    @Override
    public Diag updateDiag(String id, Diag diag) {
        if (diagRepository.existsById(id)) {
            diag.setId(id);
            return diagRepository.save(diag);
        }
        return null;
    }

    @Override
    public void deleteDiag(String id) {
        diagRepository.deleteById(id);
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