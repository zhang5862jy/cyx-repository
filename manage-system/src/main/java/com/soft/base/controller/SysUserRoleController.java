package com.soft.base.controller;

import com.soft.base.request.SetRoleForUserRequest;
import com.soft.base.resultapi.R;
import com.soft.base.service.SysUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/10/25 18:51
 **/
@RestController
@RequestMapping(value = "/userole")
@Tag(name = "用户角色")
@Slf4j
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    public SysUserRoleController(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @PostMapping(value = "/setRoleForUser")
    @Operation(summary = "用户赋角色")
    public R setRoleForUser(@RequestBody SetRoleForUserRequest request) {
        if (request.getRoleId() == null) {
            return R.fail("角色id不能为空");
        }
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            return R.fail("用户id不能为空");
        }
        try {
            sysUserRoleService.setRoleForUser(request);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
