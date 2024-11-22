package com.soft.base.conf;

import com.soft.base.websocket.WebSocketInterceptor;
import com.soft.base.websocket.handle.CustomWebSocketHandlerDecorator;
import com.soft.base.websocket.handle.WebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author: cyx
 * @Description: websocket配置类
 * @DateTime: 2024/11/21 19:44
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketHandlerDecorator(new WebSocketHandler()), "/ws")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
