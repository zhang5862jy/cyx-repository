package com.soft.base.websocket;

import com.soft.base.websocket.handleservice.WebSocketConcreteHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: cyx
 * @Description: websocket具体逻辑统一管理
 * @DateTime: 2024/11/22 10:04
 **/
public class WebSocketConcreteHolder {

    public final static Map<String, WebSocketConcreteHandler> CONCRETE_HANDLER_MAP = new ConcurrentHashMap<>();

    /**
     * 获取具体逻辑处理器
     * @param order
     * @return
     */
    public static WebSocketConcreteHandler getConcreteHandler(String order) {
        return CONCRETE_HANDLER_MAP.get(order);
    }
}
