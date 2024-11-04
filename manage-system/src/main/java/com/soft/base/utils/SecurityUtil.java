package com.soft.base.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/10/25 16:37
 **/

@Component
public class SecurityUtil {

    /**
     * 从上下文获取用户信息
     * @return
     */
    public User getUserInfo() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 从上下文获取用户角色
     * @return
     */
    public List<String> getUserRole() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

    }
}
