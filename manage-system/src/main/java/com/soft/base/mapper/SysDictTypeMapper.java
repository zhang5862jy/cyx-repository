package com.soft.base.mapper;

import com.soft.base.entity.SysDictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.base.vo.DictsVo;

import java.util.List;

/**
* @author cyq
* @description 针对表【sys_dict_type】的数据库操作Mapper
* @createDate 2024-11-04 15:51:07
* @Entity com.soft.base.entity.SysDictType
*/
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    List<DictsVo> getdicts();
}




