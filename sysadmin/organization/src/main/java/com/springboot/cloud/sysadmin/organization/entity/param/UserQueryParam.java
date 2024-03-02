package com.springboot.cloud.sysadmin.organization.entity.param;

import com.springboot.cloud.common.web.entity.param.BaseParam;
import com.springboot.cloud.sysadmin.organization.entity.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // @AllArgsConstructor 是一个由Lombok提供的注解，用于在Java类中自动生成一个包含所有成员变量的构造器（构造方法）。
                    // 这意味着，使用@AllArgsConstructor注解后，Lombok会为该类生成一个构造函数，
                    // 该构造函数包含类中定义的所有属性作为参数，从而避免了手动编写这样一个构造函数。
@NoArgsConstructor  // @NoArgsConstructor 是由Lombok库提供的一个注解，用于自动生成一个无参数的构造函数（默认构造器）在Java类中。
                    // 当你在类上应用这个注解时，Lombok会自动为该类生成一个不带任何参数的构造方法。
                    // 这个构造方法可以创建类的实例而不需要提供任何初始值。
public class UserQueryParam extends BaseParam<User> {
    /*
    这个类被设计用来封装用户查询操作时可能需要的参数，比如按名称、手机号或用户名进行搜索。
    这种方式使得在进行数据库查询或业务逻辑处理时，可以将查询条件组织得更为清晰和结构化。
    */
    private String name;
    private String mobile;
    private String username;

    // 新增用于查询用户类型
    private Integer usertype;
}
