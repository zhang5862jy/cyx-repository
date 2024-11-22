package com.soft.base.websocket.handleservice.impl;

import com.alibaba.fastjson2.JSON;
import com.soft.base.dto.UserDto;
import com.soft.base.dto.WebSocketMsgDto;
import com.soft.base.enums.WebSocketOrderEnum;
import com.soft.base.websocket.WebSocketSessionManager;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
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
    public void handle(WebSocketSession session, WebSocketMsgDto webSocketMsg) throws IOException {
        session.sendMessage(new TextMessage(JSON.toJSONString(webSocketMsg)));

        UserDto userDto = (UserDto) session.getAttributes().get(WEBSOCKET_USER);
        WebSocketSessionManager.removeSession(userDto.getId());
        log.info("remove {} session...", userDto.getUsername());
        redisTemplate.delete(WS_USER_SESSION + userDto.getId());
        log.info("{} offline...", userDto.getUsername());
    }

    @Override
    public WebSocketOrderEnum getOrder() {
        return FORCE_OFFLINE;
    }
}
