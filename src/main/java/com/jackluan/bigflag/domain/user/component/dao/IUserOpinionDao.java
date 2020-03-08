package com.jackluan.bigflag.domain.user.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.user.component.dataobject.UserOpinionDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 16:45
 */
@Mapper
public interface IUserOpinionDao extends IBaseDao<UserOpinionDo> {
}
