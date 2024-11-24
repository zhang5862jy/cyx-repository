package com.soft.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.soft.base.constants.BaseConstant;
import com.soft.base.constants.RedisConstant;
import com.soft.base.entity.SysUser;
import com.soft.base.exception.CaptChaErrorException;
import com.soft.base.exception.GlobelException;
import com.soft.base.mapper.SysUsersMapper;
import com.soft.base.request.LoginRequest;
import com.soft.base.service.AuthService;
import com.soft.base.utils.AESUtil;
import com.soft.base.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final AESUtil aesUtil;

    private final SysUsersMapper sysUsersMapper;

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           AESUtil aesUtil,
                           SysUsersMapper sysUsersMapper,
                           AuthenticationManager authenticationManager,
                           RedisTemplate<String,Object> redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.aesUtil = aesUtil;
        this.sysUsersMapper = sysUsersMapper;
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void register(SysUser sysUser) throws GlobelException {
        try {
            if (sysUsersMapper.exists(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, sysUser.getUsername()))) {
                throw new GlobelException("用户已存在");
            }

            String username = sysUsersMapper.getManager(BaseConstant.MANAGER_ROLE_CODE);
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
            if (BaseConstant.LOGIN_METHOD_PASSWORD.equals(request.getLoginMethod())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername()
                        , aesUtil.decrypt(request.getPassword())));
            } else if (BaseConstant.LOGIN_METHOD_EMAIL.equals(request.getLoginMethod())) {
                String captCha = (String) redisTemplate.opsForValue().get(RedisConstant.EMAIL_CAPTCHA_KEY + request.getUsername());
                if (!request.getPassword().equals(captCha)) {
                    throw new CaptChaErrorException("验证码错误");
                }
                redisTemplate.delete(RedisConstant.EMAIL_CAPTCHA_KEY + request.getUsername());
            }
            LoginVo loginVo = new LoginVo();
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(RedisConstant.AUTHORIZATION_USERNAME + token, request.getUsername(), RedisConstant.AUTHORIZATION_EXPIRE, TimeUnit.SECONDS);
            loginVo.setToken(TOKEN_PREFIX + token);
            loginVo.setUsername(request.getUsername());
            return loginVo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
