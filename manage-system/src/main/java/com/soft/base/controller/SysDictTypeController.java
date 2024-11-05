package com.soft.base.controller;

import com.soft.base.request.DeleteRequest;
import com.soft.base.request.EditDictTypeRequest;
import com.soft.base.request.SaveDictTypeRequest;
import com.soft.base.resultapi.R;
import com.soft.base.service.SysDictTypeService;
import com.soft.base.vo.DictTypeVo;
import com.soft.base.vo.DictTypesVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "字典类型")
@Slf4j
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    @Autowired
    public SysDictTypeController(SysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
    }

    @GetMapping
    @Operation(summary = "获取字典类型")
    public R<List<DictTypesVo>> getDictTypes() {
        try {
            List<DictTypesVo> dictTypesVos = sysDictTypeService.getdictTypes();
            return R.ok(dictTypesVos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PostMapping
    @Operation(summary = "添加字典类型")
    public R saveDictType(@RequestBody SaveDictTypeRequest request) {
        if (StringUtils.isBlank(request.getDictName())) {
            return R.fail("字典名称不能为空");
        }
        if (StringUtils.isBlank(request.getDictType())) {
            return R.fail("字典类型不能为空");
        }
        try {
            sysDictTypeService.saveDictType(request);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @PutMapping
    @Operation(summary = "编辑字典类型")
    public R editDictType(@RequestBody EditDictTypeRequest request) {
        if (request.getId() == null) {
            return R.fail("id不能为空");
        }
        try {
            sysDictTypeService.editDictType(request);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "获取字典类型（单）")
    public R<DictTypeVo> getDictType(@Schema(description = "主键") @PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("id不能为空");
        }
        try {
            DictTypeVo dictTypeVo = sysDictTypeService.getDictType(id);
            return R.ok(dictTypeVo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "删除字典类型")
    public R deleteDictType(@Schema(description = "主键") @PathVariable(value = "id") Long id) {
        if (id == null) {
            return R.fail("id不能为空");
        }
        try {
            sysDictTypeService.deleteDictType(id);
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }

    @DeleteMapping(value = "/deleteDictTypeBatch")
    @Operation(summary = "批量删除字典类型")
    public R deleteDictTypeBatch(@RequestBody DeleteRequest request) {
        if (request.getIds() == null || request.getIds().isEmpty()) {
            return R.fail("ids不能为空");
        }
        try {
            sysDictTypeService.deleteDictTypeBatch(request.getIds());
            return R.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail();
        }
    }
}
