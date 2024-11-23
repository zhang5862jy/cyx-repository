package com.soft.base.websocket.receive;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * @Author: cyx
 * @Description: 强制离线接收参数
 * @DateTime: 2024/11/23 21:56
 **/
@Data
public class ForceOfflineRecParams {

    /**
     * 指令
     */
    @NotNull
    private String order;

    /**
     * 接收者
     */
    @Nullable
    private Long receiver;
}
