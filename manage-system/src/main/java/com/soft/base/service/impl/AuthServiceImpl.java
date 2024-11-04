package com.soft.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.soft.base.entity.SysUser;
import com.soft.base.exception.GlobelException;
import com.soft.base.mapper.SysUsersMapper;
import com.soft.base.service.AuthService;
import com.soft.base.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private PasswordEncoder passwordEncoder;
    private AESUtil aesUtil;
    private SysUsersMapper sysUsersMapper;

    @Autowired
    AuthServiceImpl(PasswordEncoder passwordEncoder,
                    AESUtil aesUtil,
                    SysUsersMapper sysUsersMapper) {
        this.passwordEncoder = passwordEncoder;
        this.aesUtil = aesUtil;
        this.sysUsersMapper = sysUsersMapper;
    }

    @Override
    public void register(SysUser sysUser) {
        try {
            if (sysUsersMapper.selectCount(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, sysUser.getUsername())) > 0) {
                throw new GlobelException("用户已存在");
            }

            // 解密密码
            String decrypt = aesUtil.decrypt(sysUser.getPassword());
            // 使用BCrypt 算法加密密码
            String encode = passwordEncoder.encode(decrypt);
            sysUser.setPassword(encode);
            // 设置默认值
            sysUser.setDefault();
            sysUsersMapper.insert(sysUser);
        } catch (Exception e) {
            throw new GlobelException(e.getMessage());
        }
    }
}
