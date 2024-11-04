package com.soft.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.base.entity.SysUserRole;
import com.soft.base.request.SetRoleForUserRequest;
import com.soft.base.service.SysUserRoleService;
import com.soft.base.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author cyq
* @description 针对表【sys_user_role】的数据库操作Service实现
* @createDate 2024-10-25 18:46:14
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

    private final SysUserRoleMapper sysUserRoleMapper;

    public SysUserRoleServiceImpl(SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Override
    public void setRoleForUser(SetRoleForUserRequest request) {
        List<SysUserRole> userRoles = request
                .getUserId()
                .stream()
                .map(item -> new SysUserRole(request.getRoleId(), item))
                .collect(Collectors.toList());
        sysUserRoleMapper.setRoleForUser(userRoles);
    }
}




