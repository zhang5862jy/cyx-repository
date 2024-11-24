package com.soft.base.websocket.send;

import lombok.Data;

/**
 * @Author: cyx
 * @Description: 推送消息发送参数
 * @DateTime: 2024/11/23 23:09
 **/
@Data
public class PushMessageSendParams extends AbstractSendParams {

    /**
     * 消息
     */
    private String message;
}