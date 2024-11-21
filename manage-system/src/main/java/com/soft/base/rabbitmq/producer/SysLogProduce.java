package com.soft.base.rabbitmq.producer;

import com.soft.base.dto.LogDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.soft.base.constants.BaseConstant.BLANK_CHARACTER;
import static com.soft.base.constants.RabbitmqConstant.DIRECT_EXCHANGE;
import static com.soft.base.constants.RabbitmqConstant.DIRECT_ROUTEKEY_ONE;
import static com.soft.base.constants.RedisConstant.SYS_LOG_CACHE;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/11/21 13:59
 **/

@Component
public class SysLogProduce {

    private final RabbitTemplate rabbitTemplate;

    public final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SysLogProduce(RabbitTemplate rabbitTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 推送日志
     * @param logDto
     */
    public void sendSysLog(LogDto logDto) {
        String key = SYS_LOG_CACHE + System.currentTimeMillis();
        redisTemplate.opsForValue().set(key, logDto);
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTEKEY_ONE, key);
    }
}
