package com.springboot.cloud.nsclcservice.nsclc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BusConfig {
    @Value("${spring.application.name}")
    private String appName;

    // 定义队列名称、交换机名称和路由键的常量
    public static final String QUEUE_NAME = "event-organization";
    public static final String EXCHANGE_NAME = "spring-boot-exchange";
    public static final String RESOURCE_ROUTING_KEY = "organization-resource";
    public static final String PERMISSION_ROUTING_KEY = "organization-permission";
    private static final String PERMISSION_QUEUE_SUFFIX = "permission";

    // 定义一个Bean，创建并返回一个队列对象。这个队列不是持久的（第二个参数是false）
    @Bean
    Queue queue() {
        log.info("queue name:{}", QUEUE_NAME);
        return new Queue(QUEUE_NAME, false);
    }
//
//    @Bean
//    Queue permissionQueue() {
//        String queueName = new Base64UrlNamingStrategy(appName + ".").generateName() + PERMISSION_QUEUE_SUFFIX;
//        log.info("permission queue name:{}", queueName);
//        return new Queue(queueName, false);
//    }
//
//    /**
//     * 绑定权限更新的队列
//     *
//     * @param queue    队列
//     * @param exchange 交换机
//     * @return {@link Binding}
//     */
//    @Bean
//    Binding permissionBinding(@Qualifier("permissionQueue") Queue queue, TopicExchange exchange) {
//        log.info("binding {} to {} with {}", queue, exchange, PERMISSION_ROUTING_KEY);
//        return BindingBuilder.bind(queue).to(exchange).with(PERMISSION_ROUTING_KEY);
//    }
//
//
//    ////////////////////
//    ////////////////////  权限更新相关配置
//    ////////////////////
//    @Bean
//    SimpleMessageListenerContainer permissionMessageListenerContainer(ConnectionFactory connectionFactory, @Qualifier("permissionMessageListenerAdapter") MessageListenerAdapter messageListenerAdapter, @Qualifier("permissionQueue") Queue queue) {
//        log.info("init permissionMessageListenerContainer {}", queue.getName());
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames(queue.getName());
//        container.setMessageListener(messageListenerAdapter);
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter permissionMessageListenerAdapter(PermissionBusReceiver permissionBusReceiver, @Qualifier("permissionMessageConverter") MessageConverter messageConverter) {
//        log.info("new listener");
//        return new MessageListenerAdapter(permissionBusReceiver, messageConverter);
//    }
//
//    @Bean
//    public MessageConverter permissionMessageConverter() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
//    }

    // 定义一个Bean，创建并返回一个TopicExchange对象。Topic交换机可以根据消息的routing key将消息路由到一个或多个队列
    @Bean
    TopicExchange exchange() {
        log.info("exchange:{}", EXCHANGE_NAME);
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 定义一个Bean，创建并返回一个绑定对象。这个绑定定义了队列与交换机的绑定关系，并指定了routing key
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        log.info("binding {} to {} with {}", queue, exchange, RESOURCE_ROUTING_KEY);
        return BindingBuilder.bind(queue).to(exchange).with(RESOURCE_ROUTING_KEY);
    }

    // 定义一个Bean，创建并返回一个消息转换器对象。这个转换器用于将Java对象转换为消息体格式，这里使用的是JSON格式
    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
    }

}
