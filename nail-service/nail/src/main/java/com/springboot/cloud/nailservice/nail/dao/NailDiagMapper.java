package com.springboot.cloud.nailservice.nail.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NailDiagMapper extends BaseMapper<NailDiag>{
}
