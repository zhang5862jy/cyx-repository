package com.soft.base.conf;

import com.soft.base.websocket.handle.CustomWebSocketHandlerDecorator;
import com.soft.base.websocket.handle.WebSocketHandler;
import com.soft.base.websocket.WebSocketInterceptor;
import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

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
