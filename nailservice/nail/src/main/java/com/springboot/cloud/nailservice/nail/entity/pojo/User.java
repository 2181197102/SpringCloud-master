package com.springboot.auth.authorization.entity;

import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false )  // callSuper = false：
                                        // 表示在生成的equals和hashCode方法中不会调用超类的equals和hashCode方法。
                                        // 这是默认行为，适用于当当前类没有继承任何具有关联状态的超类（除了Object类）时。
                                        // callSuper = true：
                                        // 表示生成的equals和hashCode方法将会调用其超类的equals和hashCode方法。
                                        // 这通常在当前类是继承自另一个具有重要状态字段的类时使用，确保这些状态也被考虑在内。
@NoArgsConstructor
public class User extends BasePo {
    private String name;
    private String mobile;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
}