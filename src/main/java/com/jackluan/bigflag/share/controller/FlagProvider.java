package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.share.IFlagShareService;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.share.service.IFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:27
 */
@RestController
public class FlagProvider implements IFlagShareService {

    @Autowired
    private IFlagService flagService;

    @Override
    public ResultBase<FlagResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) {
        return flagService.createFlag(flagCreateShareRequestDto);
    }

}
