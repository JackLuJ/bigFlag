package com.jackluan.bigflag.domain.sign.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignDo;
import com.jackluan.bigflag.domain.sign.component.dataobject.extra.SignExtraDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 22:14
 */
@Mapper
public interface ISignDao extends IBaseDao<SignDo> {

    int selectApproveSignCount(SignDo signDo);

    List<SignExtraDo> selectApproveSignList(SignDo signDo);

    int selectSignCountWithDate(SignExtraDo signExtraDo);

    List<SignDo> selectSignListWithDate(SignExtraDo signExtraDo);

}
