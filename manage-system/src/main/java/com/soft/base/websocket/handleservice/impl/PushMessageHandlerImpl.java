package com.soft.base.websocket.handleservice.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.soft.base.enums.WebSocketOrderEnum;
import com.soft.base.websocket.WebSocketSessionManager;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
import com.soft.base.websocket.receive.PushMessageRecParams;
import com.soft.base.websocket.send.PushMessageSendParams;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
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
    public void handle(WebSocketSession session, TextMessage message) throws IOException {
        PushMessageRecParams pushMessageRecParams = JSON.parseObject(message.getPayload(), PushMessageRecParams.class);
        JSONArray receivers = pushMessageRecParams.getReceivers();
        PushMessageSendParams pushMessageSendParams = new PushMessageSendParams();
        pushMessageSendParams.setMessage(pushMessageRecParams.getMessage());
        for (int i = 0; i < receivers.size(); i++) {
            WebSocketSession sendSession = WebSocketSessionManager.getSession(receivers.getLong(i));
            if (sendSession == null) {
                continue;
            }
            sendSession.sendMessage(new TextMessage(pushMessageSendParams.toJsonString()));
        }
    }

    @Override
    public @NotNull WebSocketOrderEnum getOrder() {
        return PUSH_MESSAGE;
    }
}
