package com.jackluan.bigflag.domain.notice.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.notice.Logic.NoticeConfigLogic;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 21:29
 */
@Component
public class NoticeConfigHandler {

    @Autowired
    private NoticeConfigLogic noticeConfigLogic;

    public ResultBase<NoticeConfigResponseDto> createNoticeConfig(NoticeConfigRequestDto requestDto) throws BigFlagRuntimeException{
        long noticeConfigId = noticeConfigLogic.createNoticeConfig(requestDto);
        if (noticeConfigId < 1){
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_NOTICE_CONFIG_FAILED);
        }
        NoticeConfigResponseDto responseDto = new NoticeConfigResponseDto();
        responseDto.setId(noticeConfigId);
        return new ResultBase<NoticeConfigResponseDto>().success(responseDto);
    }
}
