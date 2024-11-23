package com.soft.base.websocket.schedule;

import com.soft.base.websocket.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.soft.base.constants.RedisConstant.WILDCARD_CHARACTER;
import static com.soft.base.constants.RedisConstant.WS_USER_SESSION;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/11/23 17:31
 **/

@Slf4j
@Component
public class WebsocketSessionTimer {

    private final RedisTemplate<String, Object> redisTemplate;

    public WebsocketSessionTimer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 每天零点清理一次用户会话
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUserSession() {
        Set<String> keys = redisTemplate.keys(WS_USER_SESSION + WILDCARD_CHARACTER);
        if (keys == null || keys.isEmpty()) {
            WebSocketSessionManager.clear();
        } else {
            WebSocketSessionManager
                    .getKeys()
                    .stream()
                    .filter(item -> !keys.contains(item))
                    .collect(Collectors.toSet())
                    .forEach(item -> WebSocketSessionManager
                            .removeSession(Long.parseLong(item)));
        }
    }
}
