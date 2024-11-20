package com.soft.base.handle;

import com.soft.base.resultapi.R;
import com.soft.base.utils.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.soft.base.constants.HttpConstant.FORBIDDEN;
import static com.soft.base.enums.ResultEnum.PERMISSION_NOT_ENOUGH;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/20 20:24
 **/

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtil.writeErrMsg(response, FORBIDDEN, R.fail(PERMISSION_NOT_ENOUGH.getCode(), PERMISSION_NOT_ENOUGH.getMessage()));
    }
}
