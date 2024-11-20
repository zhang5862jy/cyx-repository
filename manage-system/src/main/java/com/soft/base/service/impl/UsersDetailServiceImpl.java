package com.soft.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.soft.base.entity.SysUser;
import com.soft.base.exception.ServiceException;
import com.soft.base.mapper.SysRoleMapper;
import com.soft.base.mapper.SysUsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.soft.base.constants.BaseConstant.*;

/**
* @author cyq
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-09-30 15:49:52
*/
@Service
@Slf4j
@CacheConfig(cacheNames = "users")
public class UsersDetailServiceImpl implements UserDetailsService{

    private final SysUsersMapper sysUsersMapper;

    private final SysRoleMapper sysRoleMapper;

    @Autowired
    public UsersDetailServiceImpl(SysUsersMapper sysUsersMapper, SysRoleMapper sysRoleMapper) {
        this.sysUsersMapper = sysUsersMapper;
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    @Cacheable(key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUsersMapper.selectOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, username).or().eq(SysUser::getEmail, username));
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 角色集合
        List<String> roleCodes = sysRoleMapper.getRoleCodeByUserId(sysUser.getId());

        return new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getEnabled(),
                sysUser.getAccountNonExpired(),
                sysUser.getCredentialsNonExpired(),
                sysUser.getAccountNonLocked(),
                roleCodes.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}




