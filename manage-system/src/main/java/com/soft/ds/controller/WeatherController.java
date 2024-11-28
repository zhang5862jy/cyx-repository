package com.soft.ds.controller;

import com.soft.base.resultapi.R;
import com.soft.ds.request.WeatherRequet;
import com.soft.ds.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024/10/20 23:17
 * @Version: 1.0
 */
@RestController
@Tag(name = "天气")
@RequestMapping("/weather")
public class WeatherController {
    @GetMapping("/getWeather")
    @Operation(summary = "获取天气")
    public R getWeather(@RequestParam WeatherRequet requet) {
        WeatherService weather = new WeatherService();
        return R.ok(weather.getWeather(requet.getCityName()));
    }
}
