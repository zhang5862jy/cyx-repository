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
    FORCE_OFFLINE("force_offline"),
    /**
     * 推送消息
     */
    PUSH_MESSAGE("push_message"),
    ;

    private final String code;

    WebSocketOrderEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
