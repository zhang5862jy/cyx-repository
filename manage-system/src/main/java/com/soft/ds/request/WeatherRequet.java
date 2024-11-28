package com.soft.ds.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author: zjy
 * @Description: TODO
 * @Date: 2024/11/28 10:43
 * @Version: 1.0
 */
@Data
@Schema(description = "获取天气")
@Alias(value = "WeatherRequet")
public class WeatherRequet {

    @Schema(description = "城市名称")
    private String cityName;
}
