package com.soft.base.controller;

import com.soft.base.exception.RepeatSendCaptChaException;
import com.soft.base.resultapi.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

import static com.soft.base.constants.RabbitmqConstant.DIRECT_EXCHANGE;
import static com.soft.base.constants.RabbitmqConstant.DIRECT_ROUTEKEY_ONE;
import static com.soft.base.constants.RedisConstant.EMAIL_CAPTCHA_KEY;
import static com.soft.base.constants.RegexConstant.EMAIL;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/15 18:04
 **/

@RestController
@RequestMapping(value = "/message")
@Slf4j
@Tag(name = "消息队列")
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    private final RedisTemplate<String,String> redisTemplate;

    @Autowired
    public MessageController(RabbitTemplate rabbitTemplate,
                             RedisTemplate<String,String> redisTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping(value = "/sendRegistCaptCha")
    @Operation(summary = "发送注册验证码")
    public R sendRegistCaptCha(@RequestParam(value = "email", required = false) String email) {
        if (StringUtils.isBlank(email)) {
            return R.fail("邮箱不能为空");
        }
        if (!Pattern.matches(EMAIL, email)) {
            return R.fail("非法邮箱");
        }
        try {
            if (StringUtils.isNotBlank(redisTemplate.opsForValue().get(EMAIL_CAPTCHA_KEY + email))) {
                throw new RepeatSendCaptChaException("请勿重复发送验证码");
            }
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTEKEY_ONE, email);
            return R.ok("验证码已发送，请留意您的邮箱");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
