package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.share.dto.request.SignShareRequestDto;
import com.jackluan.bigflag.share.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.share.dto.response.SignShareResponseDto;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:48
 */
public interface ISignService {

    ResultBase<CreateSignShareResponseDto> createSign(CreateSignShareRequestDto createSignShareRequestDto);

    ResultBase<Page<SignShareResponseDto>> querySign(Page<SignShareRequestDto> signShareRequestDtoPage);

}
