package com.jackluan.bigflag.domain.notice.Logic;

import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.domain.notice.component.dao.INoticeTraceDao;
import com.jackluan.bigflag.domain.notice.component.dataobject.NoticeTraceDo;
import com.jackluan.bigflag.domain.notice.convert.NoticeTraceConvert;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeTraceRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 11:02
 */
@Slf4j
@Component
public class NoticeTraceLogic {
    private JsonConverter converter = new JsonConverter();

    @Autowired
    private INoticeTraceDao noticeTraceDao;

    public long insert(NoticeTraceRequestDto noticeTraceRequestDto) {
        NoticeTraceDo noticeTraceDo = NoticeTraceConvert.INSTANCE.convertToDo(noticeTraceRequestDto);
        int count = noticeTraceDao.insert(noticeTraceDo);
        if (count < 1) {
            log.error("create notice trace failed. param:{}",converter.objToJson(noticeTraceRequestDto));
            return count;
        }
        return noticeTraceDo.getId();
    }

}
