package com.soft.base.service;

import com.soft.base.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.base.vo.MenusVo;
import com.soft.base.vo.PageVo;

/**
* @author cyq
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2024-11-09 23:49:58
*/
public interface SysMenuService extends IService<SysMenu> {

    PageVo<MenusVo> getMenus();
}
