package com.soft.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.base.entity.SysDictType;
import com.soft.base.request.SaveDictRequest;
import com.soft.base.service.SysDictTypeService;
import com.soft.base.mapper.SysDictTypeMapper;
import com.soft.base.vo.DictsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author cyq
* @description 针对表【sys_dict_type】的数据库操作Service实现
* @createDate 2024-11-04 15:51:07
*/
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType>
    implements SysDictTypeService{

    private final SysDictTypeMapper sysDictTypeMapper;

    public SysDictTypeServiceImpl(SysDictTypeMapper sysDictTypeMapper) {
        this.sysDictTypeMapper = sysDictTypeMapper;
    }

    @Override
    public List<DictsVo> getdicts() {
        return sysDictTypeMapper.getdicts();
    }

    @Override
    public void saveDict(SaveDictRequest request) {
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(request, sysDictType);
        sysDictTypeMapper.insert(sysDictType);
    }
}




