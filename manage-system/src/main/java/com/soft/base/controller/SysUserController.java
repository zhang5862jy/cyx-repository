package com.soft.base.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.base.request.EditPasswordRequest;
import com.soft.base.request.ResetPasswordRequest;
import com.soft.base.resultapi.R;
import com.soft.base.request.PageRequest;
import com.soft.base.service.SysUsersService;
import com.soft.base.utils.AESUtil;
import com.soft.base.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.soft.base.constants.RedisConstant.TOKEN_BLACKLIST;

@RestController
@RequestMapping(value = "/user")
@Slf4j
@Tag(name = "用户接口")
public class SysUserController {

    private final SysUsersService sysUsersService;

    private final AESUtil aesUtil;

    private final SecurityUtil securityUtil;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SysUserController(SysUsersService sysUsersService,
                             AESUtil aesUtil,
                             SecurityUtil securityUtil,
                             PasswordEncoder passwordEncoder) {
        this.sysUsersService = sysUsersService;
        this.aesUtil = aesUtil;
        this.securityUtil = securityUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/getAllUsers")
    @Operation(summary = "获取所有用户")
    public R<Map<String,Object>> getAllUsers(@RequestBody PageRequest request) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Page<Map<String,Object>> mapPage = sysUsersService.getAllUsers(request);
            List<String> userRole = securityUtil.getUserRole();
            resultMap.put("userRole", userRole);
            resultMap.put("data", mapPage.getRecords());
            resultMap.put("total", mapPage.getTotal());
            return R.ok(resultMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }

    }

    @PutMapping(value = "/editPassword")
    @Operation(summary = "修改密码")
    public R editPassword(@RequestBody EditPasswordRequest request) {
        if (StringUtils.isBlank(request.getOriginalPass())) {
            return R.fail("原密码不能为空");
        }
        if (StringUtils.isBlank(request.getTargetPass())) {
            return R.fail("新密码不能为空");
        }
        try {
            String originalDecrypt = aesUtil.decrypt(request.getOriginalPass());
            String password = securityUtil.getUserInfo().getPassword();
            if (!passwordEncoder.matches(originalDecrypt, password)) {
                return R.fail("原密码不正确");
            }
            sysUsersService.editPassword(request.getTargetPass());
            return R.ok("修改成功", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PutMapping(value = "/resetPassword")
    @Operation(summary = "重置密码")
    public R resetPassword(@RequestBody ResetPasswordRequest request) {
        if (StringUtils.isBlank(request.getPassword())) {
            return R.fail("新密码不能为空");
        }
        try {
            sysUsersService.resetPassword(request);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @GetMapping(value = "/getCurUserInfo")
    @Operation(summary = "获取当前登录用户信息")
    public R<User> getCurUserInfo() {
        try {
            return R.ok(securityUtil.getUserInfo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

}
