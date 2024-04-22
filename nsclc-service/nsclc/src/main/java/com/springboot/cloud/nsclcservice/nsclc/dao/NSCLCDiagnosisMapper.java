package com.springboot.cloud.nsclcservice.nsclc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cloud.nsclcservice.nsclc.entity.po.NSCLCDiagnosis;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NSCLCDiagnosisMapper extends BaseMapper<NSCLCDiagnosis> {
}
