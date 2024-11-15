package com.soft.base.conf;

import com.soft.base.constants.RabbitmqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.soft.base.constants.RabbitmqConstant.*;

/**
 * @Author: 程益祥
 * @Description: rabbitmq配置
 * @DateTime: 2024/11/15 11:19
 **/

@Configuration
public class RabbitMQConfig {

    @Bean(name = "directQueueOne")
    public Queue directQueueOne() {
        return new Queue(DIRECT_QUEUE_ONE, false, false, false);
    }

    @Bean(name = "directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, false, false);
    }

    @Bean(name = "directBindingOne")
    public Binding directBindingOne() {
        return BindingBuilder
                .bind(directQueueOne())
                .to(directExchange())
                .with(DIRECT_ROUTEKEY_ONE);
    }
}
