package com.soft.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/4 16:01
 **/
@Schema(description = "字典")
@Data
public class DictsVo {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "字典名称")
    private String dictName;

    @Schema(description = "字典类型")
    private String dictType;
}
