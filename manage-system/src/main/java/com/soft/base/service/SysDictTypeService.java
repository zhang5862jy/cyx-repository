package com.soft.base.service;

import com.soft.base.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.base.request.SaveDictRequest;
import com.soft.base.vo.DictsVo;

import java.util.List;

/**
* @author cyq
* @description 针对表【sys_dict_type】的数据库操作Service
* @createDate 2024-11-04 15:51:07
*/
public interface SysDictTypeService extends IService<SysDictType> {

    List<DictsVo> getdicts();

    void saveDict(SaveDictRequest request);
}
