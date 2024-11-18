package com.soft.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 菜单信息表
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * 菜单唯一标识符，主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，顶级菜单的父ID通常为0
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 前端路由路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 前端组件路径
     */
    @TableField(value = "component")
    private String component;

    /**
     * 菜单图标（如字体图标类名）
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 菜单类型：0-目录，1-菜单，2-按钮
     */
    @TableField(value = "type")
    private String type;

    /**
     * 排序号，数字越小，排序越靠前
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 菜单状态：0-禁用，1-启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 是否显示：0-隐藏，1-显示
     */
    @TableField(value = "visible")
    private Integer visible;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 逻辑删除；1：存在；0：删除
     */
    @TableField(value = "del_flag")
    private String delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}