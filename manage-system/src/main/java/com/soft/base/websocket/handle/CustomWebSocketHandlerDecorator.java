package com.soft.base.websocket.handle;

import com.soft.base.dto.UserDto;
import com.soft.base.websocket.WebSocketSessionManager;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import static com.soft.base.constants.WebSocketConstant.WEBSOCKET_USER;

/**
 * @Author: cyx
 * @Description: websocket装饰器，用于处理连接和断开操作
 * @DateTime: 2024/11/21 23:12
 **/
public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator {
    public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketSessionManager.addSession(generateKey(session), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        WebSocketSessionManager.removeSession(generateKey(session));
    }

    /**
     * 生成session唯一标识
     * @param session
     * @return
     */
    private Long generateKey(WebSocketSession session) {
        UserDto userDto = (UserDto) session.getAttributes().get(WEBSOCKET_USER);
        return userDto.getId();
    }
}
