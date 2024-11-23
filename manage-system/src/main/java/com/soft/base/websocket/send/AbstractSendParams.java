package com.soft.base.websocket.send;

import com.alibaba.fastjson2.JSON;

/**
 * @Author: cyx
 * @Description: 发送消息抽象类
 * @DateTime: 2024/11/23 21:36
 **/
public abstract class AbstractSendParams {

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
