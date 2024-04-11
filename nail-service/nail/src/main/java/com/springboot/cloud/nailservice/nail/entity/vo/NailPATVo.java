package com.springboot.cloud.nailservice.nail.entity.vo;


import com.springboot.cloud.nailservice.nail.entity.po.Nail;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class NailPATVo {
    public NailPATVo(Nail nail) { // 这个构造函数接受一个User对象作为参数，并将其属性拷贝到当前对象中。
        BeanUtils.copyProperties(nail, this);
    }

    private String userId;

    private String applicationId;

    private String diagResult;

    private Date createdTime;

    private Date updatedTime;

    private String createdBy;

    private String updatedBy;
}
