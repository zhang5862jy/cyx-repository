package com.soft.base.service;

import com.soft.base.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.base.request.PermissionsRequest;
import com.soft.base.vo.PageVo;
import com.soft.base.vo.PermissionsVo;

/**
* @author cyq
* @description 针对表【sys_permission】的数据库操作Service
* @createDate 2024-11-19 09:36:53
*/
public interface SysPermissionService extends IService<SysPermission> {

    PageVo<PermissionsVo> getPermissions(PermissionsRequest request);
}
