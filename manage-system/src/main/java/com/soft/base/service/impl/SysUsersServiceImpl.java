package com.soft.base.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.base.entity.SysUser;
import com.soft.base.mapper.SysUsersMapper;
import com.soft.base.request.PageRequest;
import com.soft.base.request.ResetPasswordRequest;
import com.soft.base.service.SysUsersService;
import com.soft.base.utils.AESUtil;
import com.soft.base.utils.SecurityUtil;
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
    public Page<Map<String,Object>> getAllUsers(PageRequest request) {
        Page<Map<String,Object>> page = new Page<>(request.getPageNum(), request.getPageSize());
        return sysUsersMapper.getAllUsers(page);
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
}




