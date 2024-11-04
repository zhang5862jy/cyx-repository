package com.soft.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @TableName sys_user_role
 */
@TableName(value ="sys_user_role")
@Data
@AllArgsConstructor
public class SysUserRole implements Serializable {
    /**
     * 角色主键
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 用户主键
     */
    @TableField(value = "user_id")
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}