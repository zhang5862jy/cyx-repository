package com.soft.base.service;

import com.soft.base.entity.SysUser;
import com.soft.base.exception.GlobelException;

public interface AuthService {

    void register(SysUser sysUser) throws GlobelException;
}
