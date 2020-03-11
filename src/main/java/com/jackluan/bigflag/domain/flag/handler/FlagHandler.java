package com.jackluan.bigflag.domain.flag.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.utils.CommonUtils;
import com.jackluan.bigflag.domain.flag.convert.FlagTraceConvert;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagTraceRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.logic.FlagLogic;
import com.jackluan.bigflag.domain.flag.logic.FlagTraceLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/8 15:53
 */
@Component
public class FlagHandler {

    @Autowired
    private FlagLogic flagLogic;

    @Autowired
    private FlagTraceLogic flagTraceLogic;

    public ResultBase<FlagResponseDto> createFlag(FlagRequestDto requestDto) {
        FlagResponseDto responseDto = new FlagResponseDto();
        requestDto.setSequenceNo(1);
        long flagId = flagLogic.createFlag(requestDto);
        if (flagId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FLAG_FAILED);
        }

        requestDto.setId(flagId);
        FlagTraceRequestDto flagTraceRequestDto = FlagTraceConvert.INSTANCE.convertFlag(requestDto);
        long flagTraceId = flagTraceLogic.createFlagTrace(flagTraceRequestDto);
        if (flagTraceId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FLAG_TRACE_FAILED);
        }

        responseDto.setId(flagId);
        return new ResultBase<FlagResponseDto>().success(responseDto);
    }

    public ResultBase<List<FlagResponseDto>> queryFlagList(FlagRequestDto requestDto) {
        return new ResultBase<List<FlagResponseDto>>().success(flagLogic.queryFlagList(requestDto));
    }

    public ResultBase<Page<FlagResponseDto>> queryFlagListPage(Page<FlagRequestDto> requestDto) {
        int count = flagLogic.queryCount(requestDto.getPageCondition());
        requestDto.setTotal(count);
        List<FlagResponseDto> responseList = flagLogic.queryFlagList(requestDto.getCondition());
        Page<FlagResponseDto> page = Page.mapper(requestDto);
        page.setResults(responseList);
        return new ResultBase<Page<FlagResponseDto>>().success(page);
    }

}
