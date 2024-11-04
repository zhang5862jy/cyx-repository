package com.soft.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@Schema(description = "角色")
@Alias(value = "SysRoleVo")
public class SysRoleVo {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "排序字段")
    private Integer sortOrder;

    @Schema(description = "创建人")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "修改人")
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态；1：启用；0：禁用")
    private Integer status;

    @Schema(description = "是否标记为系统默认角色；1：是；0：不是")
    private Integer isDefault;
}
