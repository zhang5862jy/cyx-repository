package com.soft.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/10/25 17:10
 **/

@Data
@Schema(description = "重置密码")
@Alias(value = "ResetPasswordRequest")
public class ResetPasswordRequest {

    private String username;

    private String password;
}
