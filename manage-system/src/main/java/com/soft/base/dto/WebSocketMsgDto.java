package com.soft.base.dto;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/11/22 9:07
 **/

@Data
@Schema(description = "websocket消息传输参数")
public class WebSocketMsgDto {

    @Schema(description = "指令")
    @NotNull
    private String order;

    @Schema(description = "接收者")
    @Nullable
    private Long receiver;

    @Schema(description = "消息体")
    @Nullable
    private JSONObject msgBody;
}
