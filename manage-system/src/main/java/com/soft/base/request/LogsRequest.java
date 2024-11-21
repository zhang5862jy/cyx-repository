package com.soft.base.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/11/21 11:28
 **/

@Data
@Schema(description = "获取日志请求参数")
public class LogsRequest extends PageRequest {

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "模块名称")
    private String moduleName;

    @Schema(description = "状态码")
    private Integer statusCode;

    @Schema(description = "开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}