package com.soft.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/18 18:05
 **/

@Data
@Schema(description = "所有用户相应参数")
@Alias(value = "AllUserVo")
public class AllUserVo {
}
