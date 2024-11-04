package com.soft.base.handle;

import com.soft.base.constants.HttpConstant;
import com.soft.base.utils.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.soft.base.constants.RedisConstant.TOKEN_BLACKLIST;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/10/25 23:04
 **/

@Component
@Slf4j
public class LogoutAfterSuccessHandler implements LogoutSuccessHandler {

    private final RedisTemplate<String,String> redisTemplate;

    public LogoutAfterSuccessHandler(RedisTemplate<String,String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        redisTemplate.opsForSet().add(TOKEN_BLACKLIST,authorization);
        log.info("token already join blacklist...");
        ResponseUtil.writeErrMsg(response, HttpConstant.SUCCESS, "注销成功");
    }
}
