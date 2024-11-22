package com.soft.base.websocket;

import com.soft.base.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

import static com.soft.base.constants.WebSocketConstant.WEBSOCKET_USER;


/**
 * @Author: cyx
 * @Description: websocket拦截器
 * @DateTime: 2024/11/21 20:02
 **/
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.error("authentication is null...");
            return false;
        }
        // 记录发送用户
        UserDto user = (UserDto) authentication.getPrincipal();
        attributes.put(WEBSOCKET_USER, user);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
