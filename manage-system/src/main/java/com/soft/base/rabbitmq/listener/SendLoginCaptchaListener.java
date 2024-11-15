package com.soft.base.rabbitmq.listener;

import com.soft.base.utils.UniversalUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.soft.base.constants.BaseConstant.LOGIN_CAPTCHAT_LENGTH;
import static com.soft.base.constants.RabbitmqConstant.TOPIC_QUEUE_SEND_REGIST_CAPTCHA;
import static com.soft.base.constants.RedisConstant.EMAIL_CAPTCHA_KEY;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/15 19:20
 **/

@Slf4j
@Component
public class SendLoginCaptchaListener {

    private final RedisTemplate<String,String> redisTemplate;

    private final UniversalUtil universalUtil;

    private final JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    @Value(value = "${manage-system.captcha.topic}")
    private String topic;

    @Value(value = "${manage-system.captcha.expire-time}")
    private Long expireTime;

    @Autowired
    public SendLoginCaptchaListener(RedisTemplate<String,String> redisTemplate,
                                    UniversalUtil universalUtil,
                                    JavaMailSender javaMailSender) {
        this.redisTemplate = redisTemplate;
        this.universalUtil = universalUtil;
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = TOPIC_QUEUE_SEND_REGIST_CAPTCHA)
    public void onMessage(Message message) {
        try {
            log.info("start consume message...");
            String email = new String(message.getBody());
            String captChat = universalUtil.generate(LOGIN_CAPTCHAT_LENGTH);
            String captchainfo = "<!DOCTYPE html> <html lang='zh-CN'> <head> <meta charset='UTF-8'> <meta name='viewport' content='width=device-width, initial-scale=1.0'> <title>验证码邮件</title> </head> <body> <p>尊敬的用户您好！</p> <p>您的验证码是：<strong>" + captChat + "</strong>，请您在1分钟内完成验证。</p> <p>如果该验证码不是您本人申请的，请忽略此邮件。</p> <p>感谢您的使用！</p> <p>此邮件由系统自动发送，请勿回复。</p> </body> </html>";
            redisTemplate.opsForValue().set(EMAIL_CAPTCHA_KEY + email, captChat, expireTime, TimeUnit.SECONDS);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject(topic);
            helper.setText(captchainfo, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
