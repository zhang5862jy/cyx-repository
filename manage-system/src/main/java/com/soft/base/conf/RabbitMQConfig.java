package com.soft.base.conf;

import org.springframework.amqp.core.*;
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

    @Bean(name = "directQueue")
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

    @Bean(name = "topicQueueRegist")
    public Queue topicQueueRegist() {
        return new Queue(TOPIC_QUEUE_SEND_REGIST_CAPTCHA, false, false, false);
    }

    @Bean(name = "topicQueueLogin")
    public Queue topicQueueLogin() {
        return new Queue(TOPIC_QUEUE_SEND_LOGIN_CAPTCHA, false, false, false);
    }

    @Bean(name = "topicQueueDead")
    public Queue topicQueueDead() {
        return new Queue(TOPIC_QUEUE_SEND_DEAD, false, false, false);
    }

    @Bean(name = "topicExchange")
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, false, false);
    }

    @Bean(name = "topicBindingLogin")
    public Binding topicBindingLogin() {
        return BindingBuilder
                .bind(topicQueueLogin())
                .to(topicExchange())
                .with(TOPIC_ROUTE_KEY_LOGIN);
    }

    @Bean(name = "topicBindingRegist")
    public Binding topicBindingRegist() {
        return BindingBuilder
                .bind(topicQueueRegist())
                .to(topicExchange())
                .with(TOPIC_ROUTE_KEY_REGIST);
    }

    @Bean(name = "topicBindingDead")
    public Binding topicBindingDead() {
        return BindingBuilder
                .bind(topicQueueDead())
                .to(topicExchange())
                .with(TOPIC_ROUTE_KEY_DEAD);
    }
}
