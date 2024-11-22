package com.soft.base.websocket;

import com.soft.base.dto.UserDto;
import com.soft.base.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX;
import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX_LENGTH;
import static com.soft.base.constants.WebSocketConstant.HEADER_AUTHORIZATION;
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

    public WebSocketInterceptor(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        List<String> subProtocols = request.getHeaders().get(HEADER_AUTHORIZATION);
        if (subProtocols != null && !subProtocols.isEmpty()) {
            // 获取第一个子协议，作为鉴权
            String token = subProtocols.get(0);
            // 检查 Token
            if (StringUtils.isNotBlank(token) && token.startsWith(TOKEN_PREFIX)) {
                // 去除token前缀
                token = token.substring(TOKEN_PREFIX_LENGTH);
                if (jwtUtil.validateToken(token)) {
                    log.error("token is expire...");
                    return false;
                }
                String username = jwtUtil.extractUsername(token);
                UserDto user = (UserDto) userDetailsService.loadUserByUsername(username);
                attributes.put(WEBSOCKET_USER, user);
                return true;
            } else {
                log.error("valid token...");
            }
        } else {
            log.error("empty token...");
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
