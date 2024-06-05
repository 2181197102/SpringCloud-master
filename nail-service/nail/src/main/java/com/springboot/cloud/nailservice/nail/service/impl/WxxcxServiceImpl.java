package com.springboot.cloud.nailservice.nail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.nailservice.nail.dao.NailDiagMapper;
import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.service.IWxxcxService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class WxxcxServiceImpl extends ServiceImpl<NailDiagMapper, NailDiag>  implements IWxxcxService {

    // 其他方法实现

    @Override
    public IPage<NailDiag> fuzzyQuery(Page page, NailDiagQueryParam queryParam) {
        LambdaQueryWrapper<NailDiag> lqw = new LambdaQueryWrapper<>();

        // 固定参数openId
        lqw.eq(NailDiag::getOpenId, queryParam.getOpenId());

        // 可选模糊查询参数
        lqw.like(StringUtils.isNotBlank(queryParam.getDiagnosisCode()), NailDiag::getDiagnosisCode, queryParam.getDiagnosisCode());
        lqw.like(StringUtils.isNotBlank(queryParam.getDoctorName()), NailDiag::getDoctorName, queryParam.getDoctorName());
        lqw.like(StringUtils.isNotBlank(queryParam.getPatientName()), NailDiag::getPatientName, queryParam.getPatientName());
//      lqw.eq(queryParam.getResultAccuracy() != null, NailDiag::getResultAccuracy, queryParam.getResultAccuracy());
        lqw.eq(verify(queryParam.getResultAccuracy()), NailDiag::getResultAccuracy, queryParam.getResultAccuracy());
        IPage<NailDiag> retPage = this.page(page, lqw);
        return retPage;
    }


    private boolean verify(Integer result) {
        return result != null && result != -1;
    }

}


