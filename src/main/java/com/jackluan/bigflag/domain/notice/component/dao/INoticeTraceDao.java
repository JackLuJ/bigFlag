package com.jackluan.bigflag.domain.notice.component.dao;

import com.jackluan.bigflag.common.base.IBaseDao;
import com.jackluan.bigflag.domain.notice.component.dataobject.NoticeTraceDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 19:14
 */
@Mapper
public interface INoticeTraceDao extends IBaseDao<NoticeTraceDo> {
}
