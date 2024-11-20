package com.soft.base.request;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/20 14:04
 **/

@Data
@Schema(description = "添加字典数据请求参数")
public class SaveDictDataRequest {

    @Schema(description = "排序")
    private Integer sortOrder;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "键值")
    private String value;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "样式属性（其他样式扩展）")
    private String cssClass;

    @Schema(description = "表格回显样式")
    private String listClass;

    @Schema(description = "是否默认；1：是 0：否")
    private String isDefault;

    @Schema(description = "状态；1：启用；0：停用")
    private String status;

    @Schema(description = "备注")
    private String remark;
}