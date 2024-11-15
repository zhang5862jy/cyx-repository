package com.soft.base.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(2001, "成功"),
    FAIL_NORMAL(5001, "服务异常，请联系管理员"),
    AUTHLICATION_FAIL(5002, "认证失败，请重新登录"),
    ;

    private final Integer code;

    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
