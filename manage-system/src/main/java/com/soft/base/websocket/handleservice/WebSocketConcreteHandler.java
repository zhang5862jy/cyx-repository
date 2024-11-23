package com.soft.base.websocket.handleservice;

import com.soft.base.enums.WebSocketOrderEnum;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @Author: cyx
 * @Description: websocket具体业务接口
 * @DateTime: 2024/11/22 0:14
 **/
@Validated
public interface WebSocketConcreteHandler {

    void handle(WebSocketSession session, TextMessage message) throws IOException;

    @NotNull WebSocketOrderEnum getOrder();
}
