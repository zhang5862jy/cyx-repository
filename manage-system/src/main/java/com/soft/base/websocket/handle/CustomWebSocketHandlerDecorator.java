package com.soft.base.websocket.handle;

import com.soft.base.dto.UserDto;
import com.soft.base.websocket.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

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
        WebSocketSessionManager.addSession(userDto.getId(), session);
        log.info("{} connect...", userDto.getUsername());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        UserDto userDto = (UserDto) session.getAttributes().get(WEBSOCKET_USER);
        WebSocketSessionManager.removeSession(userDto.getId());
        log.info("{} connect closed...", userDto.getUsername());
    }
}
