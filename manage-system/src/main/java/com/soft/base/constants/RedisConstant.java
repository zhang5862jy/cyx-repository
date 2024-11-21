package com.soft.base.constants;

/**
 * @Author: cyx
 * @Description: redis常量类
 * @DateTime: 2024/10/25 17:32
 **/
public class RedisConstant {

    /**
     * 黑名单
     */
    public final static String TOKEN_BLACKLIST_KEY = "cyx:token:blacklist";

    /**
     * 邮箱验证码
     */
    public final static String EMAIL_CAPTCHA_KEY = "cyx:email:captcha:";

    /**
     * 保存日志key
     */
    public final static String SYS_LOG_CACHE = "cyx:log:";

    /**
     * 用户信息
     */
    public final static String USER_INFO = "users::";
}
