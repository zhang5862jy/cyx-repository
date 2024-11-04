package com.soft.base.mapper;

import com.soft.base.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author cyq
* @description 针对表【sys_user_role】的数据库操作Mapper
* @createDate 2024-10-25 18:46:14
* @Entity com.soft.base.entity.SysUserRole
*/
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    void setRoleForUser(@Param("userRoles") List<SysUserRole> userRoles);
}




