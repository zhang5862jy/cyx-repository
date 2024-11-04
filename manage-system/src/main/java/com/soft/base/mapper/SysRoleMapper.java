package com.soft.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft.base.entity.SysRole;
import com.soft.base.request.GetRolesRequest;
import com.soft.base.vo.SysRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author cyq
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2024-10-25 09:40:00
* @Entity com.soft.base.entity.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    void deleteRoleBatch(@Param("ids") List<Long> ids);

    SysRoleVo getRole(@Param("id") Long id);

    Page<SysRoleVo> getRoles(@Param("page") Page<SysRoleVo> page,
                             @Param("request") GetRolesRequest request);

    void enableRole(@Param("id") Long id);

    void forbiddenRole(@Param("id") Long id);

    void setDefaultRole(@Param("id") Long id);

    void cancelDefaultRole();

    List<String> getRoleCodeByUserId(@Param("userId") Long id);
}




