package com.jackluan.bigflag.domain.user.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.user.component.dataobject.UserDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 17:44
 */
@Mapper
public interface IUserDao extends IBaseDao<UserDo> {
}
