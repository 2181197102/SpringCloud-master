package com.springboot.cloud.nailservice.nail.config;

import org.springframework.context.annotation.Bean;
import feign.Logger;

public class FeiginClientConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
