package com.soft.base.enums;

/**
 * @Author: cyx
 * @Description: websocket指令集
 * @DateTime: 2024/11/22 10:49
 **/
public enum WebSocketOrderEnum {

    /**
     * 下线指令
     */
    FORCE_OFFLINE,
    /**
     * 推送消息
     */
    PUSH_MESSAGE,

    /**
     * 心跳检测
     */
    HEART_BEAT,

    /**
     * 聊天
     **/
    CHAT,
}
