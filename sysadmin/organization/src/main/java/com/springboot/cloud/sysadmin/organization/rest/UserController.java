package com.springboot.cloud.sysadmin.organization.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.sysadmin.organization.entity.form.UserForm;
import com.springboot.cloud.sysadmin.organization.entity.form.UserQueryForm;
import com.springboot.cloud.sysadmin.organization.entity.form.UserUpdateForm;
import com.springboot.cloud.sysadmin.organization.entity.param.UserQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.User;
import com.springboot.cloud.sysadmin.organization.service.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController // @RestController是Spring MVC提供的一个注解，它是一个组合注解，用于简化创建RESTful服务的HTTP请求处理类的开发。它结合了@Controller和@ResponseBody两个注解的功能：
                // @Controller: 用于定义一个控制器类，让Spring识别它为Spring MVC控制器的一部分。@Controller注解的类可以使用@RequestMapping等相关注解来处理HTTP请求。
                // @ResponseBody: 通常与@Controller一起使用，表示方法的返回值应该绑定到web响应体。使用@ResponseBody注解，Spring会处理方法返回的对象，将其转换为JSON或XML等格式的响应体，然后发送给客户端。
                // 当你在一个类上使用@RestController注解时，Spring会将该类视为控制器（Controller），并且假定每个处理方法的返回值都自动使用@ResponseBody注解。这意味着你不需要在每个方法上分别添加@ResponseBody注解。
@RequestMapping("/user") // @RequestMapping("/user")是Spring Framework中用于映射HTTP请求到控制器处理方法的注解。
                            // 当放置在类级别上时，它指定了一个基础的请求路径，该路径将作为类中所有方法的公共前缀。
                            // 具体来说，@RequestMapping("/user")表示所有在此控制器中定义的处理方法都将映射到以/user为前缀的URL上。
@Api("user") // @Api是Swagger2的注解，用于对Controller进行标注，表示这个类是Swagger2的资源。
            // @Api注解的属性value表示资源名称，tags表示资源的标签，description表示资源的描述。
            // 例如：@Api(value = "用户管理", tags = "用户管理", description = "用户管理相关API")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增用户", notes = "新增一个用户")
    @ApiImplicitParam(name = "userForm", value = "新增用户form表单", required = true, dataType = "UserForm")
/*  @ApiImplicitParam具有多个属性来描述参数，包括但不限于：
    name：参数的名称。
    value：参数的简短描述。
    dataType：参数的数据类型。
    paramType：参数的类型，如query（查询参数）、header（头参数）、path（路径参数）、body（请求体）、form（表单参数）等。
    example：参数的示例值。
    required：标记参数是否为必须。
    defaultValue：参数的默认值。*/
    @PostMapping
/*  @PostMapping注解支持几个重要的属性，包括：
    value 或 path: 用于指定请求的URI。这两个属性是等效的，可以用来设置请求映射的路径。
    consumes: 指定处理方法可以接受的请求的内容类型（Content-Type），例如application/json。
    produces: 指定响应的内容类型（Accept），告诉客户端它可以接收什么格式的数据。*/
    public Result add(@Valid @RequestBody UserForm userForm) {
        /*@RequestBody
        用途：@RequestBody注解用于将HTTP请求体中的JSON或XML数据绑定到Java对象上。它告诉Spring框架，请求体中的内容应该被解析并映射到后面跟着的对象上，这里是UserForm类的一个实例。
        工作原理：当请求到达时，Spring使用HTTP消息转换器(HttpMessageConverter)将请求体中的内容转换为指定的对象。对于JSON数据，通常使用Jackson库进行转换。
        为什么使用：使用@RequestBody可以简化接收和处理来自客户端的复杂数据的过程。它允许直接在方法参数中使用复杂类型，而不是手动解析请求体。*/

        /*@Valid
        用途：@Valid注解用于启用对跟在它后面的对象的自动验证。在这个例子中，它被用来验证UserForm对象。
        工作原理：当请求被处理时，Spring将会校验UserForm对象是否符合其上定义的约束（比如使用@NotNull、@Size等注解定义的验证规则）。如果对象不符合这些约束，将会产生一个绑定异常（BindingResult或MethodArgumentNotValidException），并且通常这会导致请求以错误响应结束。
        为什么使用：使用@Valid注解可以确保传入的数据符合业务规则的要求，如果数据无效，可以防止继续执行业务逻辑，从而增强了应用程序的健壮性和安全性。*/
        log.debug("name:{}", userForm);
        User user = userForm.toPo(User.class);
        return Result.success(userService.add(user));
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象，逻辑删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "string")
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        /*@PathVariable是Spring MVC提供的一个注解，用于将URI模板变量映射到控制器处理方法的参数上。
        当你在URI路径中使用{variableName}这样的模板变量时，可以通过@PathVariable注解获取这个变量的值，并将其赋值给方法的参数。
        作用
        @PathVariable String id的作用是从HTTP请求的URI中捕获名为id的路径变量。
        这意味着如果请求的URI是像/something/123这样的格式，其中123是要删除的用户ID，那么@PathVariable注解就会将123这个值提取出来，并将其作为字符串赋值给方法参数id。*/
        return Result.success(userService.delete(id));
    }

    @ApiOperation(value = "修改用户", notes = "修改指定用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "userUpdateForm", value = "用户实体", required = true, dataType = "UserUpdateForm")
    })
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody UserUpdateForm userUpdateForm) {
        User user = userUpdateForm.toPo(User.class);
        user.setId(id);
        return Result.success(userService.update(user));
    }

    @ApiOperation(value = "获取用户", notes = "获取指定用户信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "string")
    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(userService.get(id));
    }

    @ApiOperation(value = "获取用户", notes = "根据用户唯一标识（username or mobile）获取用户信息")
    @ApiImplicitParam(paramType = "query", name = "uniqueId", value = "用户唯一标识", required = true, dataType = "string")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    @GetMapping
    public Result query(@RequestParam String uniqueId) {
        log.debug("query with username or mobile:{}", uniqueId);
        return Result.success(userService.getByUniqueId(uniqueId));
    }

    @ApiOperation(value = "搜索用户", notes = "根据条件查询用户信息")
    @ApiImplicitParam(name = "userQueryForm", value = "用户查询参数", required = true, dataType = "UserQueryForm")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    @PostMapping(value = "/conditions")
    public Result search(@Valid @RequestBody UserQueryForm userQueryForm) {
        log.debug("search with userQueryForm:{}", userQueryForm);
        return Result.success(userService.query(userQueryForm.getPage(), userQueryForm.toParam(UserQueryParam.class)));
    }
}