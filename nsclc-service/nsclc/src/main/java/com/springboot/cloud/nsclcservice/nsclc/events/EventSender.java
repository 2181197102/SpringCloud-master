package com.springboot.cloud.nsclcservice.nsclc.events;


import com.springboot.cloud.nsclcservice.nsclc.config.BusConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class EventSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @PostConstruct
    public void init() {

        rabbitTemplate.setMessageConverter(messageConverter);
    }

    // 定义一个send方法，用于发送消息。该方法接受一个routingKey和要发送的对象。
    public void send(String routingKey, Object object) {
        // 使用log.info打印日志信息，显示正在发送的路由键和消息内容。
        // 这里假设log是类内定义的日志记录器（Logger）实例。这行可能在原代码中被省略了，通常通过如下代码初始化：
        // private static final Logger log = LoggerFactory.getLogger(EventSender.class);
        log.info("routingKey:{}=>message:{}", routingKey, object);

        // 调用rabbitTemplate的convertAndSend方法发送消息。
        // BusConfig.EXCHANGE_NAME是交换机名称，这里假设BusConfig是一个包含配置常量的类。
        // routingKey是路由键，用于指定消息发送的目的地。
        // object是要发送的消息对象，它会被messageConverter转换成相应格式后发送。
        rabbitTemplate.convertAndSend(BusConfig.EXCHANGE_NAME, routingKey, object);
    }
}