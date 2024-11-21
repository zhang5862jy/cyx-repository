package com.soft.base.annotation;

import java.lang.annotation.*;

/**
 * @Author: cyx
 * @Description: 日志注解
 * @DateTime: 2024/11/21 9:21
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    // 日志描述
    String value() default "";

    // 日志模块名称
    String module() default "";
}
