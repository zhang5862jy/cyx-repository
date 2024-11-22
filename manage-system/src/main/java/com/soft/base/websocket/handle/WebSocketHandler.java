package com.soft.base.websocket.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soft.base.dto.WebSocketMsgDto;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.soft.base.websocket.WebSocketConcreteHolder.CONCRETE_HANDLER_MAP;

/**
 * @Author: cyx
 * @Description: 负责处理消息
 * @DateTime: 2024/11/21 19:46
 **/
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper msgBody = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取消息体
        WebSocketMsgDto websocketMsg = msgBody.readValue(message.getPayload(), WebSocketMsgDto.class);
        String order = websocketMsg.getOrder();
        if (StringUtils.isBlank(order)) {
            log.error("websocket异常，指令为空");
            return;
        }
        WebSocketConcreteHandler webSocketConcreteHandler = CONCRETE_HANDLER_MAP.get(order);
        webSocketConcreteHandler.handle(session, websocketMsg);
    }
}
