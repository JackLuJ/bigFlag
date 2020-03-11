package com.jackluan.bigflag.domain.notice.Logic;

import com.jackluan.bigflag.domain.notice.component.dao.INoticeConfigDao;
import com.jackluan.bigflag.domain.notice.component.dataobject.NoticeConfigDo;
import com.jackluan.bigflag.domain.notice.convert.NoticeConfigConvert;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 22:12
 */
@Component
public class NoticeConfigLogic {

    @Autowired
    private INoticeConfigDao noticeConfigDao;

    public long createNoticeConfig(NoticeConfigRequestDto requestDto){
        NoticeConfigDo noticeConfigDo = NoticeConfigConvert.INSTANCE.convertToDo(requestDto);
        long count = noticeConfigDao.insert(noticeConfigDo);
        if (count < 1){
            return count;
        }
        return noticeConfigDo.getId();
    }
}
