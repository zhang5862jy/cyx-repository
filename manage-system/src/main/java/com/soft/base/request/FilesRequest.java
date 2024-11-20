package com.soft.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/20 16:45
 **/
@Data
@Schema(description = "获取文件（复）请求参数")
public class FilesRequest extends PageRequest {
}
