package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.FlagListShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.FlagShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.FlagUpdateRequestDto;
import com.jackluan.bigflag.provider.dto.response.FlagShareResponseDto;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/9 22:36
 */
public interface IFlagService {

    ResultBase<FlagShareResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto);

    ResultBase<Page<FlagShareResponseDto>> queryFlag(Page<FlagListShareRequestDto> flagListShareRequestDtoPage);

    ResultBase<FlagShareResponseDto> queryDetail(FlagShareRequestDto flagShareRequestDto);

    ResultBase<FlagShareResponseDto> update(FlagUpdateRequestDto flagUpdateRequestDto);

    void batchExpireFlag();

}
