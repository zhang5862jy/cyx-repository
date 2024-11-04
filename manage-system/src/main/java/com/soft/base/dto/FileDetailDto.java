package com.soft.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/10/26 18:41
 **/

@Data
@Schema(description = "文件详情")
@Alias(value = "FileDetailDto")
public class FileDetailDto {

    @Schema(description = "文件路径")
    private String objectKey;

    @Schema(description = "源文件名")
    private String originalName;
}
