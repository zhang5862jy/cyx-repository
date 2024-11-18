package com.soft.base.controller;

import com.soft.base.request.EditMenuRequest;
import com.soft.base.request.SaveMenuRequest;
import com.soft.base.resultapi.R;
import com.soft.base.service.SysMenuService;
import com.soft.base.vo.MenusVo;
import com.soft.base.vo.PageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/10 13:34
 **/

@RestController
@RequestMapping(value = "/sysMenu")
@Tag(name = "菜单")
@Slf4j
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }


    @GetMapping(value = "/getMenus")
    @Operation(summary = "获取菜单")
    public R<List<MenusVo>> getMenus() {
        try {
            List<MenusVo> pageVo = sysMenuService.getMenus();
            return R.ok(pageVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PostMapping
    @Operation(summary = "添加菜单")
    public R saveMenu(@RequestBody SaveMenuRequest request) {
        try {
            sysMenuService.saveMenu(request);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PutMapping
    @Operation(summary = "编辑菜单")
    public R editMenu(@RequestBody EditMenuRequest request) {
        if (request.getId() == null) {
            return R.fail("id不能不能为空");
        }
        try {
            sysMenuService.editMenu(request);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "删除菜单")
    @Parameter(name = "id", description = "主键", required = true, in = ParameterIn.PATH)
    public R deleteMenu(@PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("主键不能为空");
        }
        try {
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
