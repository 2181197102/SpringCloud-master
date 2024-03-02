package com.springboot.cloud.sysadmin.organization.entity.form;

import com.springboot.cloud.common.web.entity.form.BaseForm;
import com.springboot.cloud.sysadmin.organization.entity.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@ApiModel   // @ApiModel注解是由Swagger（现在称为OpenAPI）提供的，
            // 用于Spring Boot等Java应用程序中，主要目的是为了增强自动生成的API文档。
            // 这个注解被用于模型类上，以提供关于该模型的额外信息，使得生成的API文档更加清晰、易于理解。
@Data   // @Data是一个由Lombok库提供的注解，用于简化Java类的编写。当你在类上使用@Data注解时，Lombok会自动为该类生成以下组件：
       // 所有字段的Getter方法：为类中的每个字段生成一个公共的getter方法，使得可以安全地访问类的属性。
       // 所有字段的Setter方法：为类中的非final字段生成公共的setter方法，使得可以修改类的属性。
       // 一个无参的构造方法；
       // equals方法：生成一个equals方法，用于比较两个对象的内容是否相等，而不是比较它们的引用地址。
       // hashCode方法：生成一个hashCode方法，用于返回对象的哈希码。通常，如果两个对象通过equals方法比较是相等的，那么它们的哈希码也应该相同。
       // toString方法：生成一个toString方法，用于返回类的字符串表示，通常包括类名和每个字段的名字及其值。
       // 一个可以访问类中所有字段的方法。
public class UserForm extends BaseForm<User> {

    @ApiModelProperty(value = "用户账号")   // @ApiModelProperty注解是由Swagger提供的，用于对API文档中的模型属性进行描述。
                                          // 通过这个注解，可以为模型属性提供额外的信息，使得生成的API文档更加清晰、易于理解。
    @NotBlank(message = "用户名不能为空")  // @NotBlank注解是由Hibernate Validator提供的，用于验证字符串是否为null或空字符串。
                                        // 如果被注解的字符串为null或空字符串，那么验证不通过，会抛出ConstraintViolationException异常。
    @Length(min = 3, max = 20, message = "用户名长度在3到20个字符")   // @Length注解是由Hibernate Validator提供的，用于验证字符串的长度是否在指定的范围内。
                                                                  // 如果被注解的字符串长度不在指定的范围内，那么验证不通过，会抛出ConstraintViolationException异常。
    private String username;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 5, max = 20, message = "密码长度在5到20个字符")
    private String password;

    @ApiModelProperty(value = "用户手机号")
    @NotBlank(message = "用户手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String name;

    @ApiModelProperty(value = "用户描述")
    private String description;

    @ApiModelProperty(value = "用户拥有的角色id列表")
    private Set<String> roleIds;

    @ApiModelProperty(value = "用户状态，true为可用")
    private Boolean enabled = true;

    @ApiModelProperty(value = "用户账号是否过期，true为未过期")
    private Boolean accountNonExpired = true;

    @ApiModelProperty(value = "用户密码是否过期，true为未过期")
    private Boolean credentialsNonExpired = true;

    @ApiModelProperty(value = "用户账号是否被锁定，true为未锁定")
    private Boolean accountNonLocked = true;

    // 新增字段
    @ApiModelProperty(value = "用户身份附件")
    private String attach;

    // 新增字段
    @ApiModelProperty(value = "用户身份类型，0为其他（默认），1为医护人员等")
    private Integer usertype;
}
