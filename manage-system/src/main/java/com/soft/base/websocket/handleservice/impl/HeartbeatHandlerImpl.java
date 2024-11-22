package com.soft.base.websocket.handleservice.impl;

import com.soft.base.dto.WebSocketMsgDto;
import com.soft.base.enums.WebSocketOrderEnum;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static com.soft.base.enums.WebSocketOrderEnum.HEART_BEAT;

/**
 * @Author: cyx
 * @Description: 心跳检测处理类
 * @DateTime: 2024/11/22 17:17
 **/
public class HeartbeatHandlerImpl implements WebSocketConcreteHandler {
    @Override
    public void handle(WebSocketSession session, WebSocketMsgDto webSocketMsg) throws IOException {

    }

    @Override
    public WebSocketOrderEnum getOrder() {
        return HEART_BEAT;
    }
}
