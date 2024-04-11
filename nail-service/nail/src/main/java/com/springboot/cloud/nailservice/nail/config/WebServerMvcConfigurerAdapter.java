package com.springboot.cloud.nailservice.nail.config;

import com.springboot.cloud.common.web.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // @Configuration注解表明这个类是一个配置类，Spring容器会在启动时自动扫描加载这个类中的Bean定义。
                // 这样做的好处是可以在其他组件中通过@Autowired注解来引用这个组件。
public class WebServerMvcConfigurerAdapter implements WebMvcConfigurer {    // 实现WebMvcConfigurer接口，允许我们自定义Spring MVC的配置。

    // 使用@Bean注解定义一个Spring管理的Bean。
    // 这个方法创建了一个UserInterceptor的实例。UserInterceptor是一个自定义的拦截器，
    // 它实现了HandlerInterceptor接口，可以在请求处理之前、之后以及完成后进行自定义的处理。
    @Bean
    public HandlerInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    // 重写addInterceptors方法，这个方法来自WebMvcConfigurer接口。
    // 在这个方法中，我们可以向Spring MVC的处理流程中添加自定义的拦截器。
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 通过InterceptorRegistry的addInterceptor方法注册上面定义的userInterceptor。
        // 当Spring MVC处理请求时，它会根据注册的顺序调用这些拦截器。
        // 拦截器可以应用于所有请求，也可以指定匹配特定模式的请求。
        registry.addInterceptor(userInterceptor());
        // 如果需要，你还可以调用addPathPatterns方法来指定哪些路径应该被这个拦截器拦截，
        // 或者调用excludePathPatterns方法来指定哪些路径不应该被拦截。
    }
}
