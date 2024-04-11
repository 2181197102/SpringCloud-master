package com.springboot.cloud.nailservice.nail.entity.pojo;
import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false ) // 这个注解会生成equals(Object other) 和 hashCode()方法。
                                       // callSuper = false表示不调用父类的equals()和hashCode()方法。
@NoArgsConstructor
public class Application extends BasePo {

    private String app_name;
    private String description;
    private String app_icon;

}
