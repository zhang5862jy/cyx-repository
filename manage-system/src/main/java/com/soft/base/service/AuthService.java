package com.soft.base.service;

import com.soft.base.entity.SysUser;
import com.soft.base.exception.GlobelException;
import com.soft.base.request.LoginRequest;
import com.soft.base.vo.LoginVo;

public interface AuthService {

    void register(SysUser sysUser) throws GlobelException;

    LoginVo authenticate(LoginRequest request) throws RuntimeException;

    void sendCaptCha(String username) throws RuntimeException;
}
