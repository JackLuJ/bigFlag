package com.jackluan.bigflag.domain.flag.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import com.jackluan.bigflag.domain.flag.component.dataobject.extra.ApproverExtraDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 18:48
 */
@Mapper
public interface IApproverDao extends IBaseDao<ApproverDo> {

    int countByStatus(ApproverExtraDo approverExtraDo);

    List<ApproverDo> selectByStatus(ApproverExtraDo approverExtraDo);

}
