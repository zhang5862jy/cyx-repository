package com.soft.base.handle;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.soft.base.enums.ResultEnum.FAIL_NORMAL;

/**
 * 全局异常处理器
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 处理不支持的请求方法异常
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = String.format("不支持的方法：%s", ex.getMethod());
        JSONObject body = new JSONObject();
        body.put("code", FAIL_NORMAL.getCode());
        body.put("msg", message);
        body.put("data", null);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(body);
    }
}
