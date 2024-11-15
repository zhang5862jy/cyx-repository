package com.soft.base.controller;

import com.soft.base.resultapi.R;
import com.soft.base.service.SysMenuService;
import com.soft.base.vo.MenusVo;
import com.soft.base.vo.PageVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R<PageVo<MenusVo>> getMenus() {
        try {
            PageVo<MenusVo> pageVo = sysMenuService.getMenus();
            return R.ok(pageVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
