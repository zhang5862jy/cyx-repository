package com.soft.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.soft.base.entity.SysUser;
import com.soft.base.exception.CaptChaErrorException;
import com.soft.base.exception.GlobelException;
import com.soft.base.mapper.SysUsersMapper;
import com.soft.base.request.LoginRequest;
import com.soft.base.service.AuthService;
import com.soft.base.utils.AESUtil;
import com.soft.base.utils.JwtUtil;
import com.soft.base.utils.UniversalUtil;
import com.soft.base.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.soft.base.constants.BaseConstant.*;
import static com.soft.base.constants.RedisConstant.EMAIL_CAPTCHA_KEY;
import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final AESUtil aesUtil;

    private final SysUsersMapper sysUsersMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final JavaMailSender javaMailSender;

    private final RedisTemplate<String,String> redisTemplate;

    private final UniversalUtil universalUtil;

    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    @Value(value = "${manage-system.captcha.topic}")
    private String topic;

    @Value(value = "${manage-system.captcha.expire-time}")
    private Long expireTime;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           AESUtil aesUtil,
                           SysUsersMapper sysUsersMapper,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil,
                           JavaMailSender javaMailSender,
                           RedisTemplate<String,String> redisTemplate,
                           UniversalUtil universalUtil) {
        this.passwordEncoder = passwordEncoder;
        this.aesUtil = aesUtil;
        this.sysUsersMapper = sysUsersMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.javaMailSender = javaMailSender;
        this.redisTemplate = redisTemplate;
        this.universalUtil = universalUtil;
    }

    @Override
    public void register(SysUser sysUser) throws GlobelException {
        try {
            if (sysUsersMapper.exists(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, sysUser.getUsername()))) {
                throw new GlobelException("用户已存在");
            }

            String username = sysUsersMapper.getManager(MANAGER_ROLE_CODE);
            sysUser.setCreateBy(username);
            sysUser.setUpdateBy(username);
            // 解密密码
            String decrypt = aesUtil.decrypt(sysUser.getPassword());
            // 使用BCrypt 算法加密密码
            String encode = passwordEncoder.encode(decrypt);
            sysUser.setPassword(encode);
            // 设置默认值
            sysUser.setDefault();
            sysUsersMapper.insert(sysUser);
        } catch (Exception e) {
            throw new GlobelException(e.getMessage());
        }
    }

    @Override
    public LoginVo authenticate(LoginRequest request) throws RuntimeException{
        try {
            if (LOGIN_METHOD_PASSWORD.equals(request.getLoginMethod())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername()
                        , aesUtil.decrypt(request.getPassword())));
            } else if (LOGIN_METHOD_EMAIL.equals(request.getLoginMethod())) {
                String captCha = redisTemplate.opsForValue().get(EMAIL_CAPTCHA_KEY + request.getUsername());
                if (!request.getPassword().equals(captCha)) {
                    throw new CaptChaErrorException("验证码错误");
                }
                redisTemplate.delete(EMAIL_CAPTCHA_KEY + request.getUsername());
            }
            LoginVo loginVo = new LoginVo();
            loginVo.setToken(TOKEN_PREFIX + jwtUtil.generateToken(request.getUsername()));
            loginVo.setUsername(request.getUsername());
            return loginVo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
