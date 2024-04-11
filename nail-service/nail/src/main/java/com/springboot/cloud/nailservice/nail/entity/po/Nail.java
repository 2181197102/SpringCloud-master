package com.springboot.cloud.nailservice.nail.entity.po;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_nail_diag")
public class Nail extends BasePo{

    private String userId;

    private String applicationId;

    private String imageFile;

    private String diagResult;

    private Integer resultAccuracy;

    private String feedBack;

    @TableLogic
    private String deleted = "N";
}
