package com.springboot.cloud.nsclcservice.nsclc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springboot.cloud.nsclcservice.nsclc"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build(); // 创建Docket实例
    }

    // 定义一个私有方法，用于创建API的基本信息
    // 这里可以自定义标题、描述、服务条款的URL、版本等信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统管理API") // 设置文档标题
                .description("系统管理，组织人员管理、角色权限管理、岗位管理") // 设置文档描述
                .version("2.0") // 设置文档版本号
                .build(); // 构建ApiInfo实例
    }

}
