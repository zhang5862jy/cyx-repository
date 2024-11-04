package com.soft.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/10/25 18:32
 **/

@Data
@Schema(description = "用户赋角色")
@Alias(value = "SetRoleForUserRequest")
public class SetRoleForUserRequest {

    @Schema(description = "用户id")
    private List<Long> userId;

    @Schema(description = "角色id")
    private Long roleId;
}
