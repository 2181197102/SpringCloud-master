package com.springboot.cloud.nailservice.nail.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.beans.Beans;

@Configuration
public class RestTemplateConfig {

//    无法自动装配。存在多个 'ClientHttpRequestFactory' 类型的 Bean。
//    Beans:
//    ribbonClientHttpRequestFactory (RibbonAutoConfiguration .class)
//    simpleClientHttpRequestFactory (RestTemplateConfig.java)

//    RibbonClientHttpRequestFactory:
//    这个工厂类是 Ribbon 负载均衡客户端的一部分。
//    当你的服务是通过 Ribbon 进行负载均衡的时候，使用 RibbonClientHttpRequestFactory 是比较合适的选择。
//    Ribbon 是 Netflix 开源的负载均衡器，它能够在客户端实现负载均衡，根据服务的可用性和性能选择最佳的目标服务实例。
    
//    SimpleClientHttpRequestFactory:
//    这个工厂类是 Spring 提供的简单的 ClientHttpRequestFactory 实现。
//    SimpleClientHttpRequestFactory 不包含任何额外的功能，仅仅是通过 JDK 提供的标准 HttpURLConnection 来创建 HTTP 连接。
//    如果你的服务不涉及到负载均衡或者没有使用 Ribbon，而且你只需要进行简单的 HTTP 请求，那么 SimpleClientHttpRequestFactory 可以满足你的需求。
    
    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(@Qualifier("ribbonClientHttpRequestFactory") ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        return factory;
    }

}
