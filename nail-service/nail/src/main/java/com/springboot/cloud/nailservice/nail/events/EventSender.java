package com.springboot.cloud.nailservice.nail.events;

import com.springboot.cloud.nailservice.nail.config.BusConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component  // @Component是Spring框架中的一个注解，用于标注一个类为Spring容器的组件。当Spring框架的扫描器检测到@Component注解时，它会在应用程序上下文中自动注册该类的一个实例，使之成为Spring容器管理的一个Bean。这样，你就可以在应用程序的其他部分通过Spring提供的依赖注入功能来使用这个组件了。
            // @Component注解有几个派生注解，分别是@Repository、@Service和@Controller。这些派生注解的作用和@Component注解是一样的，只是为了更好地表明组件的用途。
@Slf4j
// 定义一个EventSender类，用于发送事件消息
public class EventSender {

    // @Autowired注解用于自动注入Spring容器中的bean。这里，自动注入RabbitTemplate实例，
    // RabbitTemplate是Spring AMQP提供的一个高级抽象，用于向RabbitMQ发送和接收消息。
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 同样使用@Autowired注解自动注入MessageConverter实例。
    // MessageConverter是一个接口，用于消息的转换。在发送消息前，可以将消息从一个格式转换为另一个格式。
    @Autowired
    private MessageConverter messageConverter;

    // @PostConstruct注解用于标记一个方法，该方法在类的实例化后被自动调用。
    // 通常用于执行初始化代码或依赖注入后的配置设置。
    @PostConstruct
    public void init() {
        // 在这个初始化方法中，将RabbitTemplate的MessageConverter设置为自动注入的messageConverter实例。
        // 这样，所有通过这个rabbitTemplate发送的消息都会使用这个messageConverter进行格式转换。
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