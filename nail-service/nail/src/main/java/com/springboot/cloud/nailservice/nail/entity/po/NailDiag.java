package com.springboot.cloud.nailservice.nail.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_nail_diag")
public class NailDiag extends BasePo {

    private String diagnosisCode;

    private String doctorName;

    private String patientName;

    private String imageFile; // 原始的图片文件路径字符串

    @TableField(exist = false)
    private List<String> imageFiles; // 解析后的图片文件路径列表，不映射到数据库

    private String diagResult;

    private Integer resultAccuracy;

    private String feedBack;

    private String openId;

    @TableLogic
    private String deleted = "N";
}
