package com.soft.base.websocket;

import com.soft.base.dto.UserDto;
import com.soft.base.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

import static com.soft.base.constants.RedisConstant.AUTHORIZATION_USERNAME;
import static com.soft.base.constants.WebSocketConstant.WEBSOCKET_USER;


/**
 * @Author: cyx
 * @Description: websocket拦截器
 * @DateTime: 2024/11/21 20:02
 **/
@Slf4j
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final RedisTemplate<String, Object> redisTemplate;

    public WebSocketInterceptor(JwtUtil jwtUtil, UserDetailsService userDetailsService, RedisTemplate<String, Object> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String token = servletRequest.getServletRequest().getParameter("token");
            String username = (String) redisTemplate.opsForValue().get(AUTHORIZATION_USERNAME + token);
            if (StringUtils.isEmpty(username)) {
                log.error("token is expire...");
                return false;
            }
            UserDto user = (UserDto) userDetailsService.loadUserByUsername(username);
            attributes.put(WEBSOCKET_USER, user);
        } else {
            log.info("request is not ServletServerHttpRequest type");
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
