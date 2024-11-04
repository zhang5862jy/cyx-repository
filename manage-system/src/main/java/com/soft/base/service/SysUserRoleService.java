package com.soft.base.service;

import com.soft.base.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.base.request.SetRoleForUserRequest;

/**
* @author cyq
* @description 针对表【sys_user_role】的数据库操作Service
* @createDate 2024-10-25 18:46:14
*/
public interface SysUserRoleService extends IService<SysUserRole> {

    void setRoleForUser(SetRoleForUserRequest request);
}
