package com.springboot.cloud.nailservice.nail.entity.po;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_nail_diag")
public class NailDiag extends BasePo{

    private String diagnosisCode;

    private String doctorName;

    private String patientName;

    private String imageFile;

    private String diagResult;

    private Integer resultAccuracy;

    private String feedBack;

    @TableLogic
    private String deleted = "N";
}
