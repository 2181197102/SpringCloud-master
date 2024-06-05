package com.springboot.cloud.nailservice.nail.entity.param;


import com.springboot.cloud.common.web.entity.param.BaseParam;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NailDiagQueryParam extends BaseParam<NailDiag> {
    /*
    这个类被设计用来封装用户查询操作时可能需要的参数，比如按名称、手机号或用户名进行搜索。
    这种方式使得在进行数据库查询或业务逻辑处理时，可以将查询条件组织得更为清晰和结构化。
    */
    private String diagnosisCode;

    private String doctorName;

    private String patientName;

    private Integer resultAccuracy;

    private String openId;
}
