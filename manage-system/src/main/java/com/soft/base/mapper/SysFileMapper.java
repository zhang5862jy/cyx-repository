package com.soft.base.mapper;

import com.soft.base.dto.FileDetailDto;
import com.soft.base.entity.SysFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author cyq
* @description 针对表【sys_file】的数据库操作Mapper
* @createDate 2024-10-26 15:19:23
* @Entity com.soft.base.entity.SysFile
*/
public interface SysFileMapper extends BaseMapper<SysFile> {

    FileDetailDto getFileDetailById(@Param("id") Long id);
}




