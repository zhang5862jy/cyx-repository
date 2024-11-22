package com.soft.base.websocket.handleservice;

import com.soft.base.dto.WebSocketMsgDto;
import com.soft.base.enums.WebSocketOrderEnum;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @Author: cyx
 * @Description: websocket具体业务接口
 * @DateTime: 2024/11/22 0:14
 **/
public interface WebSocketConcreteHandler {

    void handle(WebSocketSession session, WebSocketMsgDto webSocketMsg) throws IOException;

    WebSocketOrderEnum getOrder();
}
