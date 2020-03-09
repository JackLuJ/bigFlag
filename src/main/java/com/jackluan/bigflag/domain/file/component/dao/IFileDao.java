package com.jackluan.bigflag.domain.file.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.file.component.dataobject.FileDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 19:01
 */
@Mapper
public interface IFileDao extends IBaseDao<FileDo> {
}