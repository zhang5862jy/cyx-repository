package com.soft.base.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.base.entity.SysMenu;
import com.soft.base.mapper.SysMenuMapper;
import com.soft.base.service.SysMenuService;
import com.soft.base.vo.MenusVo;
import com.soft.base.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author cyq
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2024-11-09 23:49:58
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    private final SysMenuMapper sysMenuMapper;

    @Autowired
    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    public PageVo<MenusVo> getMenus() {
        Page<MenusVo> page = new Page<>();
        sysMenuMapper.getMenus();
        return null;
    }
}




