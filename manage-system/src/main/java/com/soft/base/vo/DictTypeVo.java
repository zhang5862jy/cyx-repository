package com.soft.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/5 14:46
 **/
@Schema(description = "字典类型")
@Data
public class DictTypeVo {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "字典类型名称")
    private String dictName;

    @Schema(description = "字典类型（不可更改）", hidden = true)
    private String dictType;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "备注")
    private String remark;
}
