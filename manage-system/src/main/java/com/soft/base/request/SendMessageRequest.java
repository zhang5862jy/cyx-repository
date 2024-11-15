package com.soft.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/15 19:04
 **/

@Data
@Schema(description = "发送消息请求参数")
public class SendMessageRequest {

    @Schema(description = "邮箱")
    private String email;
}
