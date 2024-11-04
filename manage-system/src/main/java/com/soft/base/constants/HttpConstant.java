package com.soft.base.constants;

public class HttpConstant {

    /**
     * 成功
     */
    public final static Integer SUCCESS = 200;

    /**
     * 未授权
     */
    public final static Integer UNAUTHORIZED = 401;

    /**
     * 服务器错误
     */
    public final static Integer SERVERERROR = 500;

    /**
     * token过期
     */
    public final static Integer FORBIDDEN = 403;

    /**
     * token过期
     */
    public final static Integer TOKEN_INVALID = 403;

    /**
     * 响应类型
     */
    public final static String CONTENT_TYPE = "application/octet-stream";

    public final static String[] HEADERS = {"attachment; filename=\"","\""};
}
