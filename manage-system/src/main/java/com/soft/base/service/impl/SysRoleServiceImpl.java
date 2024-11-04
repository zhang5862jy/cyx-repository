package com.soft.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.base.entity.SysRole;
import com.soft.base.entity.SysUserRole;
import com.soft.base.request.GetRolesRequest;
import com.soft.base.request.SetRoleForUserRequest;
import com.soft.base.service.SysRoleService;
import com.soft.base.mapper.SysRoleMapper;
import com.soft.base.vo.PageVo;
import com.soft.base.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author cyq
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2024-10-25 09:40:00
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    private final SysRoleMapper sysRoleMapper;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    public Boolean existCode(String code) {
        return sysRoleMapper.exists(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getCode, code));
    }

    @Override
    public void deleteRoleBatch(List<Long> ids) {
        sysRoleMapper.deleteRoleBatch(ids);
    }

    @Override
    public Boolean fixRoleFlag(Long id) {
        return sysRoleMapper.selectCount(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getId, id)) > 0;
    }

    @Override
    public SysRoleVo getRole(Long id) {
        return sysRoleMapper.getRole(id);
    }

    @Override
    public PageVo<SysRoleVo> getRoles(GetRolesRequest request) {
        PageVo<SysRoleVo> pageVo = new PageVo<>();
        Page<SysRoleVo> page = new Page<>(request.getPageNum(), request.getPageSize());

        page = sysRoleMapper.getRoles(page, request);

        pageVo.setResult(page.getRecords());
        pageVo.setTotal(page.getTotal());
        return pageVo;
    }

    @Override
    public void enableRole(Long id) {
        sysRoleMapper.enableRole(id);
    }

    @Override
    public void forbiddenRole(Long id) {
        sysRoleMapper.forbiddenRole(id);
    }

    @Override
    @Transactional
    public void setDefaultRole(Long id) {
        sysRoleMapper.cancelDefaultRole();
        sysRoleMapper.setDefaultRole(id);
    }
}




