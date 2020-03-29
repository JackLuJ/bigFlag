package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.share.IFlagShareService;
import com.jackluan.bigflag.share.dto.request.ConfirmApproverShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagShareRequestDto;
import com.jackluan.bigflag.share.dto.response.FlagShareResponseDto;
import com.jackluan.bigflag.share.service.IFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:27
 */
@CheckToken
@RestController
public class FlagProvider implements IFlagShareService {

    @Autowired
    private IFlagService flagService;

    @Override
    public ResultBase<FlagShareResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) {
        return flagService.createFlag(flagCreateShareRequestDto);
    }

    @Override
    public ResultBase<Page<FlagShareResponseDto>> queryFlag(Page<FlagShareRequestDto> flagShareRequestDto) {
        if (flagShareRequestDto == null || flagShareRequestDto.getCondition() == null || flagShareRequestDto.getCondition().getQueryType() == null) {
            return new ResultBase<Page<FlagShareResponseDto>>().failed(ResultCodeConstant.PARAM_VALIDATION_NOT_PASS);
        }
        return flagService.queryFlag(flagShareRequestDto);
    }

    @Override
    public ResultBase<FlagShareResponseDto> queryDetail(FlagShareRequestDto flagShareRequestDto) {
        return flagService.queryDetail(flagShareRequestDto);
    }
}
