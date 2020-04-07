package com.jackluan.bigflag.domain.sign.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignApproverDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 22:17
 */
@Mapper
public interface ISignApproverDao extends IBaseDao<SignApproverDo> {

    List<SignApproverDo> selectByResultTypeNotIn(SignApproverDo signApproverDo);

    int sumScore(SignApproverDo signApproverDo);
}
