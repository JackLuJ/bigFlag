package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import com.jackluan.bigflag.domain.notice.handler.NoticeConfigHandler;
import com.jackluan.bigflag.share.convert.FlagShareConvert;
import com.jackluan.bigflag.share.convert.NoticeShareConvert;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.share.service.IFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 22:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlagServiceImpl implements IFlagService {

    @Autowired
    private NoticeConfigHandler noticeConfigHandler;

    @Autowired
    private FlagHandler flagHandler;

    @Override
    public ResultBase<FlagResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) throws BigFlagRuntimeException {

        NoticeConfigRequestDto noticeConfigRequestDto = NoticeShareConvert.INSTANCE.convertToNoticeConfig(flagCreateShareRequestDto);
        ResultBase<NoticeConfigResponseDto> noticeResult = noticeConfigHandler.createNoticeConfig(noticeConfigRequestDto);
        if (!noticeResult.isSuccess()){
            return new ResultBase<FlagResponseDto>().failed(noticeResult);
        }

        FlagRequestDto flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(flagCreateShareRequestDto);
        flagRequestDto.setNoticeConfigId(noticeResult.getValue().getId());
        flagRequestDto.setStatus(FlagStatusEnum.IN_PROGRESS);
        return flagHandler.createFlag(flagRequestDto);
    }

}
