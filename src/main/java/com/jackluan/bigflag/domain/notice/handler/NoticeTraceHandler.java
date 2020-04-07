package com.jackluan.bigflag.domain.notice.handler;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.notice.Logic.NoticeTraceLogic;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeTraceRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 11:02
 */
@Component
public class NoticeTraceHandler {

    @Autowired
    private NoticeTraceLogic noticeTraceLogic;

    public ResultBase<Void> createNoticeTrace(NoticeTraceRequestDto noticeTraceRequestDto){
        long id = noticeTraceLogic.insert(noticeTraceRequestDto);
        if (id < 1){
            return new ResultBase<Void>().failed(ResultCodeConstant.CREATE_NOTICE_TRACE_FAILED);
        }else{
            return new ResultBase<Void>().success();
        }
    }

}
