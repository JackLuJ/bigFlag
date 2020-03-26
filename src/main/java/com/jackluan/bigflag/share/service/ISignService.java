package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.share.dto.response.CreateSignShareResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:48
 */
public interface ISignService {

    ResultBase<CreateSignShareResponseDto> createSign(CreateSignShareRequestDto createSignShareRequestDto);

}
