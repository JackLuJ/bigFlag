package com.jackluan.bigflag.domain.file.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.file.component.dataobject.FileGroupDo;
import com.jackluan.bigflag.domain.file.component.dataobject.extra.FileGroupExtraDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 19:01
 */
@Mapper
public interface IFileGroupDao extends IBaseDao<FileGroupDo> {

    List<FileGroupExtraDo> selectWithFile(FileGroupDo fileGroupDo);
}
