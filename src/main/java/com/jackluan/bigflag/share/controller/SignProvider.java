package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.ISignShareService;
import com.jackluan.bigflag.share.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.share.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.share.service.ISignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:45
 */
@CheckToken
@RestController
public class SignProvider implements ISignShareService {

    @Autowired
    private ISignService signService;

    @Override
    public ResultBase<CreateSignShareResponseDto> createSign(CreateSignShareRequestDto createSignShareRequestDto) {
        return signService.createSign(createSignShareRequestDto);
    }
}
