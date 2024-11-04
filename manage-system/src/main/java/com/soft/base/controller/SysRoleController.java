package com.soft.base.controller;

import com.soft.base.entity.SysRole;
import com.soft.base.request.*;
import com.soft.base.resultapi.R;
import com.soft.base.service.SysRoleService;
import com.soft.base.vo.PageVo;
import com.soft.base.vo.SysRoleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

import static com.soft.base.constants.BaseConstant.DEF_STATUS;
import static com.soft.base.constants.RegexConstant.ROLE_CODE_HEADER;

@RestController
@RequestMapping(value = "/role")
@Tag(name = "角色接口")
@Slf4j
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @PostMapping
    @Operation(summary = "新增角色")
    public R saveRole(@RequestBody SaveRoleRequest request) {
        if (StringUtils.isBlank(request.getCode())) {
            return R.fail("角色编码不能为空");
        }
        if (!Pattern.matches(ROLE_CODE_HEADER, request.getCode())) {
            return R.fail("无效的角色编码");
        }
        if (request.getStatus() == null) {
            request.setStatus(DEF_STATUS);
        }
        try {
            Boolean existCode = sysRoleService.existCode(request.getCode());
            if (existCode) {
                return R.fail("编码：" + request.getCode() + "已存在");
            }
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(request, sysRole);
            sysRoleService.save(sysRole);
            return R.ok("新增成功", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PutMapping
    @Operation(summary = "编辑角色")
    public R editRole(@RequestBody EditRoleRequest request) {
        if (request.getId() == null) {
            return R.fail("主键不能为空");
        }
        try {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(request, sysRole);
            sysRoleService.save(sysRole);
            return R.ok("编辑成功", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "删除角色")
    public R deleteRole(@Schema(description = "主键")@PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("主键不能为空");
        }
        try {
            Boolean fixRoleFlag = sysRoleService.fixRoleFlag(id);
            if (fixRoleFlag) {
                return R.fail("固定角色不可删除");
            }
            sysRoleService.removeById(id);
            return R.ok("删除成功", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @DeleteMapping(value = "/deleteRoleBatch")
    @Operation(summary = "批量删除角色")
    public R deleteRoleBatch(@RequestBody DeleteRequest request) {
        if (request.getIds() == null || request.getIds().isEmpty()) {
            return R.fail("主键不能为空");
        }
        try {
            sysRoleService.deleteRoleBatch(request.getIds());
            return R.ok("删除成功", null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "获取角色（单）")
    public R<SysRoleVo> getRole(@Schema(description = "主键")@PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("主键不能为空");
        }
        try {
            SysRoleVo sysRoleVo = sysRoleService.getRole(id);
            return R.ok(sysRoleVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PostMapping(value = "/getRoles")
    @Operation(summary = "获取角色（复）")
    public R<PageVo<SysRoleVo>> getRoles(@RequestBody GetRolesRequest request) {
        try {
            PageVo<SysRoleVo> resultPage = sysRoleService.getRoles(request);
            return R.ok(resultPage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @GetMapping(value = "/enableRole/{id}")
    @Operation(summary = "启用")
    public R enableRole(@Schema(description = "主键") @PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("主键不能为空");
        }
        try {
            sysRoleService.enableRole(id);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @GetMapping(value = "/forbiddenRole/{id}")
    @Operation(summary = "禁用")
    public R forbiddenRole(@Schema(description = "主键")@PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("主键不能为空");
        }
        try {
            sysRoleService.forbiddenRole(id);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @GetMapping(value = "/setDefaultRole/{id}")
    @Operation(summary = "设置默认角色")
    public R setDefaultRole(@Schema(description = "主键")@PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("主键不能为空");
        }
        try {
            sysRoleService.setDefaultRole(id);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
