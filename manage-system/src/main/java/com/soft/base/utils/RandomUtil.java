package com.soft.base.utils;

import org.springframework.stereotype.Component;

/**
 * @Author: 程益祥
 * @Description: 随机数工具类
 * @DateTime: 2024/11/7 17:49
 **/

@Component
public class RandomUtil {

    private static final Integer[] NATURE_NUM = {1,2,3,4,5,6,7,8,9,0};

    public String generate(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(NATURE_NUM[(int)(Math.random() * 10)]);
        }
        return sb.toString();
    }
}
