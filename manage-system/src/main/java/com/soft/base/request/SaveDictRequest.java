package com.soft.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/4 21:57
 **/

@Schema(description = "添加字典")
@Data
public class SaveDictRequest {

    @Schema(description = "字典名称")
    private String dictName;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态", hidden = true)
    private String status = "1";

    @Schema(description = "备注")
    private String remark;
}
