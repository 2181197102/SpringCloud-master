package com.springboot.cloud.nailservice.nail.service.impl;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.nailservice.nail.clients.UserClient;
import com.springboot.cloud.nailservice.nail.dao.NailDiagMapper;
import com.springboot.cloud.nailservice.nail.entity.form.UserForm;
import com.springboot.cloud.nailservice.nail.entity.param.NailDiagQueryParam;
import com.springboot.cloud.nailservice.nail.entity.param.RegisterParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import com.springboot.cloud.nailservice.nail.entity.pojo.User;
import com.springboot.cloud.nailservice.nail.service.INailService;
import com.springboot.cloud.nailservice.nail.service.IWxxcxService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Service
public class WxxcxServiceImpl extends ServiceImpl<NailDiagMapper, NailDiag>  implements IWxxcxService {

    private final UserClient userClient;

    @Autowired
    public WxxcxServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

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


    // 用户注册
    @Override
    public boolean register(RegisterParam registerParam) {
        String openid = registerParam.getOpenId();
        String nickname = registerParam.getNickName();
        String usertype = registerParam.getUserType();
        String phonenum = registerParam.getPhoneNum();
        Set<String> roleids = Objects.equals(usertype, "1") ? Collections.singleton("102") : Collections.singleton("103");

        Result<User> user = userClient.getUserByUniqueId(openid);
        String code = user.getCode();



        if (Objects.equals(code, "030100")) { // 用户未找到--》add一个用户
            UserForm wx_user = new UserForm();
            wx_user.setDescription("wx_user");
            wx_user.setName(nickname);
            wx_user.setUsername(openid);
            wx_user.setPassword(openid);
            wx_user.setMobile(phonenum);
            wx_user.setRoleIds(roleids);
            wx_user.setUsertype(usertype);

            Result addResult = userClient.add(wx_user);
            // 用户添加成功
            return addResult.isSuccess();

        }

        return true;
    }


}


