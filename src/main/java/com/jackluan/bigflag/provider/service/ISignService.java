package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.ApproveSignShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.SignShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.SignShareResponseDto;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:48
 */
public interface ISignService {

    ResultBase<CreateSignShareResponseDto> createSign(CreateSignShareRequestDto createSignShareRequestDto);

    ResultBase<Page<SignShareResponseDto>> querySign(Page<SignShareRequestDto> signShareRequestDtoPage);

    ResultBase<Void> approveSign(ApproveSignShareRequestDto approveSignShareRequestDto);

    ResultBase<Void> delete(SignShareRequestDto signShareRequestDto);
}
