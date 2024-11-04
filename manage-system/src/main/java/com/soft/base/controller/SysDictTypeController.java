package com.soft.base.controller;

import com.soft.base.request.SaveDictRequest;
import com.soft.base.resultapi.R;
import com.soft.base.service.SysDictTypeService;
import com.soft.base.vo.DictsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/4 15:57
 **/
@RestController
@RequestMapping(value = "/sysDictType")
@Tag(name = "字典")
@Slf4j
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    @Autowired
    public SysDictTypeController(SysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
    }

    @GetMapping
    @Operation(summary = "获取字典")
    public R<List<DictsVo>> getdicts() {
        try {
            List<DictsVo> dictsVos = sysDictTypeService.getdicts();
            return R.ok(dictsVos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PostMapping
    @Operation(summary = "添加字典")
    public R saveDict(@RequestBody SaveDictRequest request) {
        if (StringUtils.isBlank(request.getDictName())) {
            return R.fail("字典名称不能为空");
        }
        if (StringUtils.isBlank(request.getDictType())) {
            return R.fail("字典类型不能为空");
        }

        try {
            sysDictTypeService.saveDict(request);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
