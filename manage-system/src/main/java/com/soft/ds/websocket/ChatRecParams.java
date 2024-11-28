package com.soft.ds.websocket;

import com.alibaba.fastjson2.JSONArray;
import lombok.Data;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024/11/24 22:51
 * @Version: 1.0
 */

@Data
public class ChatRecParams {

    private String order;

    private JSONArray receivers;

    private String msgBody;
}
