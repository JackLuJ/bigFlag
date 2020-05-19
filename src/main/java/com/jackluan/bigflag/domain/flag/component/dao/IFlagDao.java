package com.jackluan.bigflag.domain.flag.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:41
 */
@Mapper
public interface IFlagDao extends IBaseDao<FlagDo> {

    int increasePerformance(FlagDo flagDo);

    int updatePassFlag(FlagDo flagDo);

    int selectApproveFlagCount(FlagDo flagDo);

    List<FlagDo> selectApproveFlagList(FlagDo flagDo);

    int updateByDeadline(FlagDo flagDo);

    List<FlagDo> selectByDeadline(FlagDo flagDo);

}
