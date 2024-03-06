package com.springboot.cloud.sysadmin.organization.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement    // @EnableTransactionManagement注解启用了Spring的注解驱动事务管理功能。
                                // 这意味着你可以在服务层组件中使用@Transactional注解来声明事务的边界。
@Configuration  // @Configuration注解表明这个类是一个配置类，Spring容器会在启动时自动扫描加载这个类中的Bean定义。
public class MybatisConfig {
    /**
     * 初始化Mybatis的SQL注入器。
     * MyBatis Plus中的ISqlInjector接口用于注入自定义SQL操作，这里使用的LogicSqlInjector是MyBatis Plus提供的逻辑删除功能的实现。
     * 逻辑删除是指在数据库中不真正删除数据，而是通过一个字段来标记数据是否被删除。
     * 使用这个功能可以很容易地恢复误删除的数据，也方便数据的审计。
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


    /**
     * 分页插件。
     * PaginationInterceptor是MyBatis Plus提供的分页插件，它可以拦截查询操作自动进行分页处理。
     * 在使用时，只需要传递分页参数（如当前页和页大小），分页插件会自动修改SQL语句进行分页查询。
     * 这样做的好处是简化了分页处理的代码，开发者不需要编写用于分页的SQL语句。
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
