package com.soft.base.exception;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/7 19:04
 **/
public class RepeatSendCaptChaException extends RuntimeException {

    private String message;

    public RepeatSendCaptChaException(String message) {
        super(message);
    }
}
