package com.soft.base.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.soft.base.service.SysUsersService;
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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.soft.base.constants.BaseConstant.LOGIN_CAPTCHAT_LENGTH;
import static com.soft.base.constants.RabbitmqConstant.TOPIC_QUEUE_SEND_LOGIN_CAPTCHA;
import static com.soft.base.constants.RabbitmqConstant.TOPIC_QUEUE_SEND_REGIST_CAPTCHA;
import static com.soft.base.constants.RedisConstant.EMAIL_CAPTCHA_KEY;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/15 19:20
 **/

@Slf4j
@Component
public class CaptchaConsume {

    private final RedisTemplate<String,String> redisTemplate;

    private final UniversalUtil universalUtil;

    private final SysUsersService sysUsersService;

    private final JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    @Value(value = "${manage-system.captcha.topic}")
    private String topic;

    @Value(value = "${manage-system.captcha.expire-time}")
    private Long expireTime;

    @Autowired
    public CaptchaConsume(RedisTemplate<String,String> redisTemplate,
                          UniversalUtil universalUtil,
                          JavaMailSender javaMailSender,
                          SysUsersService sysUsersService) {
        this.redisTemplate = redisTemplate;
        this.universalUtil = universalUtil;
        this.javaMailSender = javaMailSender;
        this.sysUsersService = sysUsersService;
    }

    @RabbitListener(queues = TOPIC_QUEUE_SEND_LOGIN_CAPTCHA, ackMode = "MANUAL")
    public void sendLoginCaptcha(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("start consume message...");
            String username = new String(message.getBody());

            String email = sysUsersService.getEmail(username);
            String captChat = universalUtil.generate(LOGIN_CAPTCHAT_LENGTH);
            redisTemplate.opsForValue().set(EMAIL_CAPTCHA_KEY + username, captChat, expireTime, TimeUnit.SECONDS);

            sendEmail(email, captChat);
            channel.basicAck(deliveryTag, false);
        } catch (MessagingException | IOException e) {
            log.error(e.getMessage(), e);
            try {
                channel.basicReject(deliveryTag, false);
            } catch (IOException ex) {
                log.error(ex.getMessage(), e);
            }

            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = TOPIC_QUEUE_SEND_REGIST_CAPTCHA, ackMode = "MANUAL")
    public void sendRegistCaptcha(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("start consume message...");
            String email = new String(message.getBody());
            String captChat = universalUtil.generate(LOGIN_CAPTCHAT_LENGTH);
            redisTemplate.opsForValue().set(EMAIL_CAPTCHA_KEY + email, captChat, expireTime, TimeUnit.SECONDS);

            sendEmail(email, captChat);
            channel.basicAck(deliveryTag, false);
        } catch (MessagingException | IOException e) {
            log.error(e.getMessage(), e);
            try {
                channel.basicReject(deliveryTag, false);
            } catch (IOException ex) {
                log.error(ex.getMessage(), e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送邮件
     * @param email
     * @param captChat
     * @throws MessagingException
     */
    private void sendEmail(String email, String captChat) throws MessagingException{
        String captchaInfo = "<!DOCTYPE html> <html lang='zh-CN'> <head> <meta charset='UTF-8'> <meta name='viewport' content='width=device-width, initial-scale=1.0'> <title>验证码邮件</title> </head> <body> <p>尊敬的用户您好！</p> <p>您的验证码是：<strong>" + captChat + "</strong>，请您在1分钟内完成验证。</p> <p>如果该验证码不是您本人申请的，请忽略此邮件。</p> <p>感谢您的使用！</p> <p>此邮件由系统自动发送，请勿回复。</p> </body> </html>";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromEmail);
        helper.setTo(email);
        helper.setSubject(topic);
        helper.setText(captchaInfo, true);
        javaMailSender.send(mimeMessage);
    }
}
