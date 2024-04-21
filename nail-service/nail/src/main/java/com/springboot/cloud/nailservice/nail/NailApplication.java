package com.springboot.cloud.nailservice.nail;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.springboot.cloud.nailservice.nail.config.FeiginClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableMethodCache(basePackages = "com.springboot.cloud")
@EnableCreateCacheAnnotation
@EnableFeignClients(defaultConfiguration = FeiginClientConfig.class) //, basePackages = {"com.springboot.cloud.nailservice.nail.config"}
public class NailApplication {
    public static void main(String[] args) {
        SpringApplication.run(NailApplication.class, args);
    }
}
