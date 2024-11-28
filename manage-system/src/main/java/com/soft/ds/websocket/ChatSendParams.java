package com.soft.ds.websocket;

import com.soft.base.websocket.send.AbstractSendParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024/11/24 23:04
 * @Version: 1.0
 */

@EqualsAndHashCode(callSuper = false)
@Data
public class ChatSendParams extends AbstractSendParams {

    private String msgBody;
}
