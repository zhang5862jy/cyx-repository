package com.soft.base.websocket.handle.message.concrete;

import com.soft.base.constants.RedisConstant;
import com.soft.base.constants.WebSocketConstant;
import com.soft.base.dto.UserDto;
import com.soft.base.enums.WebSocketOrderEnum;
import com.soft.base.websocket.handle.message.WebSocketConcreteHandler;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.soft.base.enums.WebSocketOrderEnum.HEART_BEAT;

/**
 * @Author: cyx
 * @Description: 心跳检测处理类，客户端每隔30秒调用一次
 * @DateTime: 2024/11/22 17:17
 **/
@Component
@Slf4j
public class HeartbeatHandler implements WebSocketConcreteHandler {

    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    public HeartbeatHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void handle(WebSocketSession session, TextMessage message) throws IOException {
        UserDto userDto = (UserDto) session.getAttributes().get(WebSocketConstant.WEBSOCKET_USER);
        // 重新设置用户在线状态
        redisTemplate.opsForValue().set(RedisConstant.WS_USER_SESSION + userDto.getId(), userDto.getUsername(), RedisConstant.WS_USER_SESSION_EXPIRE, TimeUnit.SECONDS);
        log.info("{} keep-live...", userDto.getUsername());
    }

    @Override
    public @NotNull WebSocketOrderEnum getOrder() {
        return WebSocketOrderEnum.HEART_BEAT;
    }
}