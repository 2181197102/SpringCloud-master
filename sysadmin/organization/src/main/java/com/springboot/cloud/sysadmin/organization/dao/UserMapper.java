package com.springboot.cloud.sysadmin.organization.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cloud.sysadmin.organization.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository // @Repository注解用于标记数据访问组件，即DAO（数据访问对象）层。
            // 它的主要作用是让Spring识别为持久层组件，从而能够进行组件扫描、自动装配等。
            // 此外，它还能够将持久层的异常转换为Spring统一的数据访问异常体系，便于异常管理和降低与特定数据库技术的耦合。
@Mapper // @Mapper注解是MyBatis框架中的一个注解，用于标识一个接口为MyBatis的映射器接口。用于映射SQL操作和实体类
        // 当使用这个注解标记一个接口时，MyBatis会在启动时自动扫描这个接口，并创建这个接口的代理实现对象。
        // 这样，开发者就可以通过注入这个接口来使用其定义的数据库操作方法，无需自己编写实现代码。
        // 这样做的好处是可以在其他组件中通过@Autowired注解来引用这个组件。
        // 这个过程大大简化了数据访问层（DAO层）的开发工作，提高了开发效率。
public interface UserMapper extends BaseMapper<User> {
}