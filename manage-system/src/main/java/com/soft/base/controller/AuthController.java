package com.soft.base.controller;

import com.soft.base.entity.SysUser;
import com.soft.base.exception.CaptChaErrorException;
import com.soft.base.request.LoginRequest;
import com.soft.base.request.RegisterRequest;
import com.soft.base.resultapi.R;
import com.soft.base.service.AuthService;
import com.soft.base.utils.AESUtil;
import com.soft.base.utils.JwtUtil;
import com.soft.base.utils.SecurityUtil;
import com.soft.base.vo.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

import static com.soft.base.constants.RegexConstant.USERNAME_PATTERN;
import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX;

@RestController
@Tag(name = "鉴权")
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "登录")
    public R<LoginVo> authenticate(@RequestBody LoginRequest request) {
        if (StringUtils.isBlank(request.getUsername())) {
            return R.fail("用户名不能为空");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            return R.fail("密码不能为空");
        }
        if (StringUtils.isBlank(request.getLoginMethod())) {
            return R.fail("登录方式不能为空");
        }
        try {
            LoginVo loginVo = authService.authenticate(request);
            return R.ok(loginVo);
        } catch (BadCredentialsException
                 | DisabledException
                 | LockedException
                 | CredentialsExpiredException
                 | AccountExpiredException
                 | CaptChaErrorException e) {
            log.error(e.getMessage(), e);
            return R.fail(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PostMapping(value = "/register")
    @Operation(summary = "注册")
    public R register(@RequestBody RegisterRequest request) {
        if (StringUtils.isBlank(request.getUsername())) {
            return R.fail("用户名不能为空");
        }
        if (!Pattern.matches(USERNAME_PATTERN, request.getUsername())) {
            return R.fail("用户名不能使用中文");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            return R.fail("密码不能为空");
        }

        try {
            SysUser sysUser = new SysUser();
            sysUser.setUsername(request.getUsername());
            sysUser.setPassword(request.getPassword());
            sysUser.setNickname(request.getNickname());
            authService.register(sysUser);
            return R.ok("注册成功", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @GetMapping(value = "/sendCaptCha")
    @Operation(summary = "获取验证码")
    public R sendCaptCha(@Schema(description = "用户名") @RequestParam(value = "username", required = false) String username) {
        if (StringUtils.isBlank(username)) {
            return R.fail("用户名不能为空");
        }
        try {
            authService.sendCaptCha(username);
            return R.ok("验证码获取成功，请留意您的邮箱");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
