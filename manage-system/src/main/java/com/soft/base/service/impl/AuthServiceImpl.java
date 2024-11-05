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

import static com.soft.base.constants.BaseConstant.MANAGER_ROLE_CODE;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AESUtil aesUtil;
    private final SysUsersMapper sysUsersMapper;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                    AESUtil aesUtil,
                    SysUsersMapper sysUsersMapper) {
        this.passwordEncoder = passwordEncoder;
        this.aesUtil = aesUtil;
        this.sysUsersMapper = sysUsersMapper;
    }

    @Override
    public void register(SysUser sysUser) throws GlobelException {
        try {
            if (sysUsersMapper.exists(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, sysUser.getUsername()))) {
                throw new GlobelException("用户已存在");
            }

            String username = sysUsersMapper.getManager(MANAGER_ROLE_CODE);
            sysUser.setCreateBy(username);
            sysUser.setUpdateBy(username);
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
