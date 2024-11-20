package com.soft.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/20 11:41
 **/
@Data
@Schema(description = "字典数据请求参数")
public class DictDatasRequest extends PageRequest{

    @Schema(description = "字典类型")
    private String dictType;
}
