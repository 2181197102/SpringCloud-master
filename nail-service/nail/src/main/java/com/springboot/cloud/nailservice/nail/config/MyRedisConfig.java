package com.springboot.cloud.nailservice.nail.config;

import com.springboot.cloud.common.web.redis.RedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration  // @Configuration注解表明这个类是一个配置类，Spring容器会在启动时自动扫描加载这个类中的Bean定义。
                // 这样做的好处是可以在其他组件中通过@Autowired注解来引用这个组件。
@EnableCaching  // @EnableCaching注解启用了Spring的缓存管理功能。
                // 这意味着你可以在服务层组件中使用@Cacheable、@CachePut和@CacheEvict等注解来声明缓存的行为。
public class MyRedisConfig extends RedisConfig {

}