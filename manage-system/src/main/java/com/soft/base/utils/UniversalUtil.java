package com.soft.base.utils;

import com.soft.base.exception.NotArrayException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @Author: 程益祥
 * @Description: 通用工具类
 * @DateTime: 2024/11/7 17:49
 **/

@Component
public class UniversalUtil {

    /**
     * 生成随机数
     * @param length
     * @return
     */
    public String generate(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double random = Math.random() * 10;
            sb.append((int) random);
        }
        return sb.toString();
    }

    /**
     * list转数组
     * @param list
     * @param valueType
     * @return
     * @param <T>
     */
    public <T> T toArray(List list, java.lang.Class<T> valueType) throws NotArrayException {
        if (!valueType.isArray()) {
            throw new NotArrayException("valueType必须是数组类型");
        }
        @SuppressWarnings("unchecked")
        T array = (T) Array.newInstance(valueType.getComponentType(), list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(array, i, list.get(i));
        }
        return array;
    }
}
