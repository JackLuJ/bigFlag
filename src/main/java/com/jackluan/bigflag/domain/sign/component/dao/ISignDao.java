package com.jackluan.bigflag.domain.sign.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 22:14
 */
@Mapper
public interface ISignDao extends IBaseDao<SignDo> {
}
