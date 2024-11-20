package com.soft.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/20 14:44
 **/

@Data
@Schema(description = "获取权限（复）请求参数")
public class PermissionsRequest extends PageRequest {

}
