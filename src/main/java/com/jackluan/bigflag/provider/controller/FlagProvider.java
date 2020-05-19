package com.jackluan.bigflag.provider.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import com.jackluan.bigflag.common.utils.ValidationUtils;
import com.jackluan.bigflag.provider.dto.request.FlagUpdateRequestDto;
import com.jackluan.bigflag.share.IFlagShareService;
import com.jackluan.bigflag.provider.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.FlagShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.FlagShareResponseDto;
import com.jackluan.bigflag.provider.service.IFlagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:27
 */
@Slf4j
@CheckToken
@RestController
public class FlagProvider implements IFlagShareService {
    private JsonConverter converter = new JsonConverter();

    @Autowired
    private IFlagService flagService;

    @Override
    public ResultBase<FlagShareResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) {
        ValidationUtils.isEmpty(flagCreateShareRequestDto);
        if (FlagTypeEnum.TIMES_FLAG == flagCreateShareRequestDto.getFlagType() && flagCreateShareRequestDto.getThreshold() == null){
            return new ResultBase<FlagShareResponseDto>().failed(ResultCodeConstant.PARAM_VALIDATION_NOT_PASS, "please set threshold");
        }
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

    @Override
    public ResultBase<FlagShareResponseDto> update(FlagUpdateRequestDto flagUpdateRequestDto) {
        return flagService.update(flagUpdateRequestDto);
    }
}