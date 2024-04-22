package com.springboot.cloud.nsclcservice.nsclc.config;

import com.springboot.cloud.common.web.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebServerMvcConfigurerAdapter implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor userInterceptor() {
        return new UserInterceptor();
    }

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
