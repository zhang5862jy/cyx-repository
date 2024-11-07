package com.soft.base.utils;

import org.springframework.stereotype.Component;

/**
 * @Author: 程益祥
 * @Description: 随机数工具类
 * @DateTime: 2024/11/7 17:49
 **/

@Component
public class RandomUtil {

    public String generate(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double random = Math.random() * 10;
            sb.append((int) random);
        }
        return sb.toString();
    }
}
