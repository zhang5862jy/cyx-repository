package com.soft.base.handle;

import com.soft.base.constants.HttpConstant;
import com.soft.base.resultapi.R;
import com.soft.base.utils.JwtUtil;
import com.soft.base.utils.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.soft.base.constants.RedisConstant.TOKEN_BLACKLIST_KEY;
import static com.soft.base.constants.RedisConstant.USER_INFO;
import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX_LENGTH;
import static com.soft.base.enums.ResultEnum.SUCCESS;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/10/25 23:04
 **/

@Component
@Slf4j
public class LogoutAfterSuccessHandler implements LogoutSuccessHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    private final JwtUtil jwtUtil;

    @Value(value = "${jwt.expire}")
    private Long jwtExpireTime;

    public LogoutAfterSuccessHandler(RedisTemplate<String, Object> redisTemplate, JwtUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        redisTemplate.opsForSet().add(TOKEN_BLACKLIST_KEY,authorization);
        log.info("token already join blacklist...");
        String username = jwtUtil.extractUsername(authorization.substring(TOKEN_PREFIX_LENGTH));
        redisTemplate.delete(USER_INFO + username);
        log.info("{} already remove in redis", username);
        ResponseUtil.writeErrMsg(response, HttpConstant.SUCCESS, R.ok(SUCCESS.getCode(), "注销成功"));
    }
}
