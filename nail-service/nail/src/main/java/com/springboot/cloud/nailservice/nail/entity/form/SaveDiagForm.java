package com.springboot.cloud.nailservice.nail.entity.form;



import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.nailservice.nail.entity.po.NailDiag;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor     // 生成一个包含所有参数的构造函数
@Builder                // 用来构建对象实例
@NoArgsConstructor      // 生成一个无参数的构造函数
public class SaveDiagForm extends BaseForm<NailDiag> {

    private String diagnosisCode;

    private String doctorName;

    private String patientName;

    private String imageFile;

    private String diagResult;

    private Integer resultAccuracy;

    private String feedBack;

    private String openId;
}
