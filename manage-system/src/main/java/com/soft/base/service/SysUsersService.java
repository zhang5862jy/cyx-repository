package com.soft.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.base.entity.SysUser;
import com.soft.base.request.PageRequest;
import com.soft.base.request.ResetPasswordRequest;

import java.util.Map;

/**
* @author cyq
* @description 针对表【users】的数据库操作Service
* @createDate 2024-09-30 15:49:52
*/
public interface SysUsersService extends IService<SysUser> {
    Page<Map<String,Object>> getAllUsers(PageRequest request);

    void editPassword(String targetPass) throws Exception;

    void resetPassword(ResetPasswordRequest request) throws Exception;
}
