package com.soft.base.websocket.handleservice.impl;

import com.soft.base.dto.WebSocketMsgDto;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static com.soft.base.enums.WebSocketOrderEnum.PUSH_MESSAGE;

/**
 * @Author: cyx
 * @Description: 推送消息处理器
 * @DateTime: 2024/11/22 10:01
 **/

@Component
public class PushMessageHandlerImpl implements WebSocketConcreteHandler {
    @Override
    public void handle(WebSocketSession session, WebSocketMsgDto webSocketMsg) throws IOException {

    }

    @Override
    public String getOrder() {
        return PUSH_MESSAGE.getCode();
    }
}
