package com.springboot.cloud.nailservice.nail.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.springboot.cloud.common.web.entity.po.BasePo;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BasePo {
    private String name;
    private String mobile;
    private String username;
    private String password;
    private String description;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;

    @TableField(exist = false)  // @TableField是MyBatis-Plus提供的注解，用于标识实体类中的字段是否为数据库表中的字段。
    // 通过exist属性，可以指定该字段是否为数据库表中的字段，如果设置为false，则表示该字段不是数据库表中的字段。
    private Set<String> roleIds;    // 用于表示一个用户所拥有的角色ID集合（set确保不重复）

    @TableField(exist = false)
    private Set<String> roles;

    @TableLogic // @TableLogic是MyBatis Plus框架提供的一个注解，用于实现逻辑删除功能。
    // 逻辑删除是相对于物理删除来说的，物理删除会直接从数据库中删除数据行，
    // 而逻辑删除只是通过更改数据行中某个字段的值来标记该行数据为“已删除”，实际数据行仍然保留在数据库表中。
    // 这样做的好处是可以保留数据的完整性和历史记录，同时对外表现得就像数据已经被删除一样。
    private String deleted = "N";   // 定义了一个名为deleted的私有字段，并且初始化为"N"。
    // 逻辑删除字段，用于标识数据行是否已被删除。默认值为“N”，表示未删除。

    // 新增字段
    private String attach; // 用户身份附件，存储文件路径或其他标识，以便于识别用户的额外身份信息。

    // 新增字段
    private String usertype; // 用户身份，0为其他，1为医护人员等。使用整型存储，便于在数据库中进行查询和索引。

    // 新增字段
    @TableField(exist = false)
    private Set<String> applicationIds;
}