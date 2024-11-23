package com.soft.base.websocket.handleservice.impl;

import com.alibaba.fastjson2.JSON;
import com.soft.base.dto.UserDto;
import com.soft.base.enums.WebSocketOrderEnum;
import com.soft.base.websocket.WebSocketSessionManager;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
import com.soft.base.websocket.receive.ForceOfflineRecParams;
import com.soft.base.websocket.send.ForceOfflineSendParams;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static com.soft.base.constants.RedisConstant.WS_USER_SESSION;
import static com.soft.base.constants.WebSocketConstant.WEBSOCKET_USER;
import static com.soft.base.enums.WebSocketOrderEnum.FORCE_OFFLINE;

/**
 * @Author: cyx
 * @Description: 强制下线处理器
 * @DateTime: 2024/11/22 0:10
 **/

@Component
@Slf4j
public class ForceOfflineHandlerImpl implements WebSocketConcreteHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ForceOfflineHandlerImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void handle(WebSocketSession session, TextMessage message) throws IOException {
        ForceOfflineRecParams forceOfflineRecParams = JSON.parseObject(message.getPayload(), ForceOfflineRecParams.class);
        WebSocketSession receiveSession = WebSocketSessionManager.getSession(forceOfflineRecParams.getReceiver());
        if (receiveSession == null) {
            log.error("接收方未连接websocket...");
            return;
        }

        ForceOfflineSendParams forceOfflineSendParams = new ForceOfflineSendParams();
        forceOfflineSendParams.setOrder(forceOfflineRecParams.getOrder());

        receiveSession.sendMessage(new TextMessage(forceOfflineSendParams.toJsonString()));

        UserDto userDto = (UserDto) receiveSession.getAttributes().get(WEBSOCKET_USER);
        WebSocketSessionManager.removeSession(userDto.getId());
        log.info("remove {} session...", userDto.getUsername());
        redisTemplate.delete(WS_USER_SESSION + userDto.getId());
        log.info("{} offline...", userDto.getUsername());
    }

    @Override
    public @NotNull WebSocketOrderEnum getOrder() {
        return FORCE_OFFLINE;
    }
}
