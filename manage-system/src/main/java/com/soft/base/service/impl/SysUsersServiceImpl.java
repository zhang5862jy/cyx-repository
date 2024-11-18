package com.soft.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.base.dto.UserRoleDto;
import com.soft.base.entity.SysUser;
import com.soft.base.mapper.SysUsersMapper;
import com.soft.base.request.*;
import com.soft.base.service.SysUsersService;
import com.soft.base.utils.AESUtil;
import com.soft.base.utils.SecurityUtil;
import com.soft.base.vo.AllUserVo;
import com.soft.base.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author cyq
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-09-30 15:49:52
*/
@Service
public class SysUsersServiceImpl extends ServiceImpl<SysUsersMapper, SysUser> implements SysUsersService {

    private final SysUsersMapper sysUsersMapper;

    private final SecurityUtil securityUtil;

    private final PasswordEncoder passwordEncoder;

    private final AESUtil aesUtil;

    @Autowired
    public SysUsersServiceImpl(SysUsersMapper sysUsersMapper,
                               SecurityUtil securityUtil,
                               PasswordEncoder passwordEncoder,
                               AESUtil aesUtil) {
        this.sysUsersMapper = sysUsersMapper;
        this.securityUtil = securityUtil;
        this.passwordEncoder = passwordEncoder;
        this.aesUtil = aesUtil;
    }

    @Override
    public PageVo<AllUserVo> getAllUsers(PageRequest request) {
        Page<AllUserVo> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<AllUserVo> allUsers = sysUsersMapper.getAllUsers(page);
        PageVo<AllUserVo> pageVo = new PageVo<>();
        pageVo.setResult(allUsers.getRecords());
        pageVo.setTotal(allUsers.getTotal());
        return pageVo;
    }

    @Override
    public void editPassword(String targetPass) throws Exception{
        String username = securityUtil.getUserInfo().getUsername();
        String encode = passwordEncoder.encode(aesUtil.decrypt(targetPass));
        sysUsersMapper.editPassword(username, encode);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) throws Exception{
        String encode = passwordEncoder.encode(aesUtil.decrypt(request.getPassword()));
        sysUsersMapper.editPassword(request.getUsername(), encode);
    }

    @Override
    public String getEmail(String username) {
        return sysUsersMapper.getEmail(username);
    }

    @Override
    public boolean checkUsernameExist(String username) {
        return sysUsersMapper.exists(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, username).or().eq(SysUser::getEmail, username));
    }

    @Override
    public void setRoleForUser(SetRoleForUserRequest request) {
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setRoleId(request.getRoleId());
        userRoleDto.setUserId(request.getUserId());
        sysUsersMapper.setRoleForUser(userRoleDto);
    }

    @Override
    public void saveUser(SaveUserRequest request) throws Exception{
        request.setPassword(passwordEncoder.encode(aesUtil.decrypt(request.getPassword())));
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(request, sysUser);
        sysUser.setDefault();
        sysUsersMapper.insert(sysUser);
    }

    @Override
    public void editUser(EditUserRequest request) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(request, sysUser);
        sysUsersMapper.updateById(sysUser);
    }
}




