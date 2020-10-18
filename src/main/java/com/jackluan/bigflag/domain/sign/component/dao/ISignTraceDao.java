package com.jackluan.bigflag.domain.sign.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignTraceDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 22:15
 */
@Mapper
public interface ISignTraceDao extends IBaseDao<SignTraceDo> {
}
