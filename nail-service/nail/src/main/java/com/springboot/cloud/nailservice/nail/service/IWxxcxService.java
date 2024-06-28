package com.springboot.cloud.nailservice.nail.service;

import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import com.springboot.cloud.nailservice.nail.entity.param.RegisterParam;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import com.springboot.cloud.nailservice.nail.entity.vo.PredictVo;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.IOException;

public interface IWxxcxService {
    // 其他方法签名

    // 定义模糊查询方法
    IPage<NailDiag> fuzzyQuery(Page page, NailDiagQueryParam queryParam);

//    // 定义更新诊断_微信小程序专属方法
//    boolean update(NailDiag nailDiag);

    // 定义用户注册方法
    boolean register(RegisterParam registerParam);
}
