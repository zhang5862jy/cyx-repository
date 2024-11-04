package com.soft.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.base.entity.SysUser;
import com.soft.base.vo.DeptUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author cyq
* @description 针对表【users】的数据库操作Mapper
* @createDate 2024-09-30 15:49:52
* @Entity com.soft.entity.Users
*/
public interface SysUsersMapper extends BaseMapper<SysUser> {
    Page<Map<String,Object>> getAllUsers(@Param("page") Page<Map<String,Object>> page);

    void editPassword(@Param("username") String username,
                      @Param("password") String password);

    List<DeptUserVo> getAllUser();
}




