package com.soft.ds.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.soft.base.enums.WebSocketOrderEnum;
import com.soft.base.websocket.WebSocketSessionManager;
import com.soft.base.websocket.handle.message.WebSocketConcreteHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024/11/24 22:48
 * @Version: 1.0
 */

@Component
public class ChatHandler implements WebSocketConcreteHandler {
    @Override
    public void handle(WebSocketSession session, TextMessage message) throws IOException {
        ChatRecParams chatRecParams = JSON.parseObject(message.getPayload(), ChatRecParams.class);
        JSONArray receivers = chatRecParams.getReceivers();
        String msgBody = chatRecParams.getMsgBody();
        String receiver;
        WebSocketSession receiverSession;
        ChatSendParams chatSendParams = new ChatSendParams();
        for (int i = 0; i < receivers.size(); i++) {
            receiver = receivers.getString(i);
            if ((receiverSession = WebSocketSessionManager.getSession(Long.parseLong(receiver))) == null) {
                continue;
            }
            chatSendParams.setMsgBody(msgBody);
            receiverSession.sendMessage(new TextMessage(chatSendParams.toJsonString()));
        }
    }

    @Override
    public WebSocketOrderEnum getOrder() {
        return WebSocketOrderEnum.CHAT;
    }
}
