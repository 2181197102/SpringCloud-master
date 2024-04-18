package com.springboot.cloud.nailservice.nail.entity.vo;

import com.springboot.cloud.common.web.entity.vo.BaseVo;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;


@Data
@NoArgsConstructor

public class NailDiagVo extends BaseVo<NailDiag>{
    public NailDiagVo(NailDiag nailDiag) { // 这个构造函数接受一个User对象作为参数，并将其属性拷贝到当前对象中。
        BeanUtils.copyProperties(nailDiag, this);
    }

    private String diagnosisCode;

    private String doctorName;

    private String patientName;

    private String imageFile;

    private String diagResult;

    private Integer resultAccuracy;

    private String feedBack;

    private Date createdTime;

    private Date updatedTime;

    private String createdBy;

    private String updatedBy;
}
