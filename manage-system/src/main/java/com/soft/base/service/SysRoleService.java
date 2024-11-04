package com.soft.base.service;

import com.soft.base.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.base.request.GetRolesRequest;
import com.soft.base.request.SetRoleForUserRequest;
import com.soft.base.vo.PageVo;
import com.soft.base.vo.SysRoleVo;

import java.util.List;

/**
* @author cyq
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2024-10-25 09:40:00
*/
public interface SysRoleService extends IService<SysRole> {

    Boolean existCode(String code);

    void deleteRoleBatch(List<Long> ids);

    Boolean fixRoleFlag(Long id);

    SysRoleVo getRole(Long id);

    PageVo<SysRoleVo> getRoles(GetRolesRequest request);

    void enableRole(Long id);

    void forbiddenRole(Long id);

    void setDefaultRole(Long id);
}
