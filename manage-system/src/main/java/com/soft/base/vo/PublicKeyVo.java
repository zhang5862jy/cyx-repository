package com.soft.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/11/26 15:02
 **/

@Data
@Schema(description = "获取公钥响应参数")
public class PublicKeyVo {

    @Schema(description = "公钥")
    private String publicKey;
}
