package com.soft.base.websocket.handleservice.impl;

import com.alibaba.fastjson2.JSON;
import com.soft.base.dto.WebSocketMsgDto;
import com.soft.base.enums.WebSocketOrderEnum;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static com.soft.base.enums.WebSocketOrderEnum.FORCE_OFFLINE;

/**
 * @Author: cyx
 * @Description: 强制下线处理器
 * @DateTime: 2024/11/22 0:10
 **/

@Component
public class ForceOfflineHandlerImpl implements WebSocketConcreteHandler {

    @Override
    public void handle(WebSocketSession session, WebSocketMsgDto webSocketMsg) throws IOException {
        session.sendMessage(new TextMessage(JSON.toJSONString(webSocketMsg)));
    }

    @Override
    public WebSocketOrderEnum getOrder() {
        return FORCE_OFFLINE;
    }
}
