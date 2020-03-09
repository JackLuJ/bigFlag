package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 22:36
 */
public interface IFlagService {

    ResultBase<FlagResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) throws BigFlagRuntimeException;

}
