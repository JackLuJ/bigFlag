package com.jackluan.bigflag.domain.notice.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.notice.Logic.NoticeConfigLogic;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 21:29
 */
@Component
public class NoticeConfigHandler {

    @Autowired
    private NoticeConfigLogic noticeConfigLogic;

    public ResultBase<NoticeConfigResponseDto> createNoticeConfig(NoticeConfigRequestDto requestDto) {
        long noticeConfigId = noticeConfigLogic.createNoticeConfig(requestDto);
        if (noticeConfigId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_NOTICE_CONFIG_FAILED);
        }
        NoticeConfigResponseDto responseDto = new NoticeConfigResponseDto();
        responseDto.setId(noticeConfigId);
        return new ResultBase<NoticeConfigResponseDto>().success(responseDto);
    }

    public ResultBase<List<NoticeConfigResponseDto>> queryNoticeConfig(NoticeConfigRequestDto requestDto) {
        return new ResultBase<List<NoticeConfigResponseDto>>().success(noticeConfigLogic.queryNoticeList(requestDto));
    }

    public ResultBase<Void> updateNotice(NoticeConfigRequestDto noticeConfigRequestDto){
        int count = noticeConfigLogic.update(noticeConfigRequestDto);
        if (count < 1){
            return new ResultBase<Void>().failed(ResultCodeConstant.UPDATE_NOTICE_CONFIG_FAILED);
        }else{
            return new ResultBase<Void>().success();
        }
    }

    public List<NoticeConfigResponseDto> queryList(NoticeConfigRequestDto noticeConfigRequestDto){
        return noticeConfigLogic.queryNoticeList(noticeConfigRequestDto);
    }

    public ResultBase<Void> delete(NoticeConfigRequestDto noticeConfigRequestDto){
        int count = noticeConfigLogic.delete(noticeConfigRequestDto);
        if (count < 1){
            return new ResultBase<Void>().failed(ResultCodeConstant.DELETE_NOTICE_CONFIG_FAILED);
        }else {
            return new ResultBase<Void>().success();
        }
    }
}
