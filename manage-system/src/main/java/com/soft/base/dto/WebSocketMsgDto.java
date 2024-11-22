package com.soft.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/11/22 9:07
 **/

@Data
@Schema(description = "websocket消息传输参数")
public class WebSocketMsgDto {

    @Schema(description = "指令")
    private String order;

    @Schema(description = "接收者")
    private Long receiver;
}
