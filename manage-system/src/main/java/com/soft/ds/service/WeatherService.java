package com.soft.ds.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.soft.ds.utils.HttpGetPost;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024/10/21 18:10
 * @Version: 1.0
 */
public class WeatherService {
    public static String getWeatherByOther(String CityName) {
        Map<String, Object> hashmap = new HashMap<String, Object>();
        String jsonStr = "";
        String strUrl = "https://cn.apihz.cn/api/tianqi/tqyb.php";
        String srtValue = "id=88888888&key=88888888&sheng=湖北&place=" + CityName;
        jsonStr = HttpGetPost.sendGet(strUrl, srtValue);
        System.out.println(jsonStr);
        return jsonStr;
    }

    public Map<String, Object> getWeather(String CityName) {
        String jsonstr = getWeatherByOther(CityName);
        JSONObject jsonObject = new JSONObject(JSON.parseObject(jsonstr));
        Object windSpeed = jsonObject.get("windSpeed");
        Object windDirection = jsonObject.get("windDirection");
        Object humidity = jsonObject.get("humidity");
        Object temperature = jsonObject.get("temperature");
        Object precipitation = jsonObject.get("precipitation");
        Object weather1 = jsonObject.get("weather1");
        Object weather2 = jsonObject.get("weather2");
        Object msg = jsonObject.get("msg");
        Map<String, Object> result = new HashMap<>();
        result.put("weather1", weather1);
        result.put("weather2", weather2);
        result.put("temperature", temperature);
        result.put("precipitation", precipitation);
        result.put("humidity", humidity);
        result.put("windSpeed", windSpeed);
        result.put("windDirection", windDirection);
        return result;
    }
}
