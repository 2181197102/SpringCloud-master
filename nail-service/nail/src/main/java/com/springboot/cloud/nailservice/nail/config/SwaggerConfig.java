package com.springboot.cloud.nailservice.nail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  // @Configuration注解表明这个类是一个配置类，Spring容器会在启动时自动扫描加载这个类中的Bean定义。
                // 这样做的好处是可以在其他组件中通过@Autowired注解来引用这个组件。
@EnableSwagger2 // @EnableSwagger2注解启用了Swagger的API文档功能。
                // 这意味着你可以在浏览器中访问http://localhost:8010/swagger-ui.html来查看系统的API文档。
public class SwaggerConfig {

    // 定义一个Bean，用于创建Swagger的Docket的Bean实例
    // Docket是Swagger中的一个主要配置类，用于配置Swagger的各种属性
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 指定文档类型使用Swagger2
                .apiInfo(apiInfo()) // 调用下面的apiInfo函数，获取API的基本信息（如标题、描述、版本等）
                .select()    // 返回一个ApiSelectorBuilder实例，用来控制哪些接口暴露给Swagger来展现
                .apis(RequestHandlerSelectors.basePackage("com.springboot.cloud.nailservice.nail")) // 指定扫描的包路径来定义指定要建立API文档的控制器类或方法
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build(); // 创建Docket实例
    }

    // 定义一个私有方法，用于创建API的基本信息
    // 这里可以自定义标题、描述、服务条款的URL、版本等信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("甲病诊断API") // 设置文档标题
                .description("接口测试") // 设置文档描述
                .termsOfServiceUrl("https://github.com/zhoutaoo/SpringCloud") // 设置服务条款的URL
                .version("2.0") // 设置文档版本号
                .build(); // 构建ApiInfo实例
    }

}
