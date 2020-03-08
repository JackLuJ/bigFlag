package com.jackluan.bigflag.domain.flag.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:41
 */
@Mapper
public interface IFlagDao extends IBaseDao<FlagDo> {
}
