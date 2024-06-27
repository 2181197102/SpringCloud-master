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

    // 模糊查询
    @Override
    public IPage<NailDiag> fuzzyQuery(Page page, NailDiagQueryParam queryParam) {
        LambdaQueryWrapper<NailDiag> lqw = new LambdaQueryWrapper<>();

        // 固定参数openId
        if (StringUtils.isNotBlank(queryParam.getOpenId())) {
            lqw.eq(NailDiag::getOpenId, queryParam.getOpenId());
        }

        // 可选模糊查询参数
        if (StringUtils.isNotBlank(queryParam.getDiagnosisCode())) {
            lqw.like(NailDiag::getDiagnosisCode, queryParam.getDiagnosisCode());
        }
        if (StringUtils.isNotBlank(queryParam.getDoctorName())) {
            lqw.like(NailDiag::getDoctorName, queryParam.getDoctorName());
        }
        if (StringUtils.isNotBlank(queryParam.getPatientName())) {
            lqw.like(NailDiag::getPatientName, queryParam.getPatientName());
        }
        if (verify(queryParam.getResultAccuracy())) {
            lqw.eq(NailDiag::getResultAccuracy, queryParam.getResultAccuracy());
        }
        IPage<NailDiag> retPage = this.page(page, lqw);
        return retPage;
    }


    private boolean verify(Integer result) {
        return result != null && result != -1;
    }

}


