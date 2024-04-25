package com.springboot.cloud.nailservice.nail.config;

import com.springboot.cloud.common.web.redis.RedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration  // @Configuration注解表明这个类是一个配置类，Spring容器会在启动时自动扫描加载这个类中的Bean定义。
                // 这样做的好处是可以在其他组件中通过@Autowired注解来引用这个组件。
@EnableCaching  // @EnableCaching注解启用了Spring的缓存管理功能。
                // 这意味着你可以在服务层组件中使用@Cacheable、@CachePut和@CacheEvict等注解来声明缓存的行为。
public class MyRedisConfig extends RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // 设置 key 和 value 的序列化器为 StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // 设置哈希键和哈希值的序列化器为 StringRedisSerializer
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}