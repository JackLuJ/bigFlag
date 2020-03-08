package com.jackluan.bigflag.domain.flag.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagTraceDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 18:46
 */
@Mapper
public interface IFlagTraceDao extends IBaseDao<FlagTraceDo> {
}
