package com.springboot.cloud.nailservice.nail.entity.pojo;
import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false )
@NoArgsConstructor
public class Application extends BasePo {

    private String app_name;
    private String description;
    private String app_icon;

}
