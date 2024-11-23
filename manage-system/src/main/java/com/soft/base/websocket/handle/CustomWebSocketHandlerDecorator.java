package com.soft.base.websocket.handle;

import com.soft.base.dto.UserDto;
import com.soft.base.websocket.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import java.util.concurrent.TimeUnit;

import static com.soft.base.constants.RedisConstant.WS_USER_SESSION;
import static com.soft.base.constants.RedisConstant.WS_USER_SESSION_EXPIRE;
import static com.soft.base.constants.WebSocketConstant.WEBSOCKET_USER;

/**
 * @Author: cyx
 * @Description: websocket装饰器，用于处理连接和断开操作
 * @DateTime: 2024/11/21 23:12
 **/
@Slf4j
public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    private final RedisTemplate<String, Object> redisTemplate;

    public CustomWebSocketHandlerDecorator(WebSocketHandler delegate, RedisTemplate<String, Object> redisTemplate) {
        super(delegate);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UserDto userDto = (UserDto) session.getAttributes().get(WEBSOCKET_USER);
        // 添加用户会话
        if (WebSocketSessionManager.getSession(userDto.getId()) == null) {
            WebSocketSessionManager.addSession(userDto.getId(), session);
        }
        // 添加用户缓存
        redisTemplate.opsForValue().set(WS_USER_SESSION + userDto.getId(), userDto.getUsername(), WS_USER_SESSION_EXPIRE, TimeUnit.SECONDS);
        log.info("{} connect...", userDto.getUsername());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        UserDto userDto = (UserDto) session.getAttributes().get(WEBSOCKET_USER);
        // 移除用户会话
        WebSocketSessionManager.removeSession(userDto.getId());
        // 移除用户缓存
        redisTemplate.delete(WS_USER_SESSION + userDto.getId());
        log.info("{} connect closed...", userDto.getUsername());
    }
}