package com.jackluan.bigflag.domain.flag.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import com.jackluan.bigflag.common.utils.CommonUtils;
import com.jackluan.bigflag.common.utils.DateUtils;
import com.jackluan.bigflag.domain.flag.convert.ApproverConvert;
import com.jackluan.bigflag.domain.flag.convert.FlagConvert;
import com.jackluan.bigflag.domain.flag.convert.FlagTraceConvert;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.CreateSingInfoRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagTraceRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.CreateSingInfoResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.UpdateFlagResponseDto;
import com.jackluan.bigflag.domain.flag.logic.ApproverLogic;
import com.jackluan.bigflag.domain.flag.logic.FlagLogic;
import com.jackluan.bigflag.domain.flag.logic.FlagTraceLogic;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
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

    @Autowired
    private ApproverLogic approverLogic;

    public ResultBase<FlagResponseDto> createFlag(FlagRequestDto requestDto) {
        FlagResponseDto responseDto = new FlagResponseDto();
        if (requestDto.getFlagType() == FlagTypeEnum.DAILY_FLAG) {
            long deadLine = requestDto.getDeadline().getTime();
            long threshold = (deadLine - System.currentTimeMillis()) / (24 * 60 * 60 * 1000) + 1;
            requestDto.setThreshold(Long.valueOf(threshold).intValue());
        }

        requestDto.setSequenceNo(1);
        requestDto.setPerformance(0);
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

        List<FlagResponseDto> responseList;
        switch (requestDto.getCondition().getQueryType()){
            case OWN:
                responseList = queryOwnFlagList(requestDto);
                break;
            case APPROVE:
                responseList = queryApproveFlagList(requestDto);
                break;
            default:
                return new ResultBase<Page<FlagResponseDto>>().success();
        }

        Boolean showApprover = requestDto.getCondition().getShowApprover();
        if (showApprover != null && showApprover) {
            responseList.forEach(flagResponseDto -> {
                ApproverRequestDto approverRequestDto = new ApproverRequestDto();
                approverRequestDto.setFlagId(flagResponseDto.getId());
                approverRequestDto.setStatus(ApproverStatusEnum.EFFECTIVE);
                List<ApproverResponseDto> approverList = approverLogic.queryApproverList(approverRequestDto);
                flagResponseDto.setApproverList(approverList);
            });
        }

        Page<FlagResponseDto> page = Page.mapper(requestDto);
        page.setResults(responseList);
        return new ResultBase<Page<FlagResponseDto>>().success(page);
    }

    public ResultBase<CreateSingInfoResponseDto> getSignInfo(CreateSingInfoRequestDto createSingInfoRequestDto) {
        FlagRequestDto requestDto = FlagConvert.INSTANCE.convert(createSingInfoRequestDto);
        List<FlagResponseDto> responseList = flagLogic.queryFlagList(requestDto);
        if (CollectionUtils.isEmpty(responseList)) {
            throw new BigFlagRuntimeException(ResultCodeConstant.FIND_FLAG_FAILED);
        }

        FlagResponseDto flag = responseList.get(0);

        ApproverRequestDto approverRequestDto = ApproverConvert.INSTANCE.convert(createSingInfoRequestDto);
        List<ApproverResponseDto> approverList = approverLogic.queryApproverList(approverRequestDto);

        BigDecimal sumScore = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(approverList)) {
            sumScore = approverList.stream().map(item -> item.getScore() == null ? BigDecimal.ZERO : new BigDecimal(item.getScore())).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        BigDecimal factor = flag.getAchieveConfigType().getFactor();
        int scale = flag.getAchieveConfigType().getScale();
        RoundingMode roundingMode = flag.getAchieveConfigType().getRoundingMode();
        Integer threshold = 1;
        if (-1 != factor.intValue()) {
            threshold = sumScore.divide(factor, scale, roundingMode).intValue();
        }

        CreateSingInfoResponseDto response = new CreateSingInfoResponseDto();
        response.setDeadline(flag.getFlagType().getDeadline());
        response.setThreshold(threshold);
        response.setApproverList(approverList);
        if (FlagTypeEnum.DAILY_FLAG == flag.getFlagType()){
            response.setCheckDailyTimes(true);
        }
        return new ResultBase<CreateSingInfoResponseDto>().success(response);
    }

    public ResultBase<UpdateFlagResponseDto> updateFlag(FlagRequestDto flagRequestDto) {
        if (null == flagRequestDto.getFlagUpdateType()) {
            throw new BigFlagRuntimeException(ResultCodeConstant.FLAG_UPDATE_TYPE_NOT_SET);
        }

        List<FlagResponseDto> flagList = flagLogic.queryFlagList(flagRequestDto);
        if (CollectionUtils.isEmpty(flagList)) {
            throw new BigFlagRuntimeException(ResultCodeConstant.FIND_FLAG_FAILED);
        }

        switch (flagRequestDto.getFlagUpdateType()) {
            case SIGN_PASS:
                updateWhenSignPass(flagRequestDto);
                break;
            case FLAG_UPDATE:
                break;
            default:
                break;
        }

        FlagRequestDto requestDto = new FlagRequestDto();
        requestDto.setId(flagRequestDto.getId());
        List<FlagResponseDto> resultList = flagLogic.queryFlagList(requestDto);
        UpdateFlagResponseDto updateFlagResponseDto = new UpdateFlagResponseDto();
        updateFlagResponseDto.setFlagStatusEnum(resultList.get(0).getStatus());
        return new ResultBase<UpdateFlagResponseDto>().success(updateFlagResponseDto);
    }


    private void updateWhenSignPass(FlagRequestDto flagRequestDto) {
        flagRequestDto.setPerformance(1);
        int count = flagLogic.increasePerformance(flagRequestDto);
        if (count < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.INCREASE_FLAG_PERFORMANCE_FAILED);
        }

        flagRequestDto.setStatus(FlagStatusEnum.FINISHED);
        flagLogic.updatePassFlag(flagRequestDto);
    }

    private List<FlagResponseDto> queryOwnFlagList(Page<FlagRequestDto> requestDto){
        int count = flagLogic.queryCount(requestDto.getPageCondition());
        requestDto.setTotal(count);
        return flagLogic.queryFlagList(requestDto.getCondition());
    }

    private List<FlagResponseDto> queryApproveFlagList(Page<FlagRequestDto> requestDto){
        int count = flagLogic.queryApproveFlagCount(requestDto.getPageCondition());
        requestDto.setTotal(count);
        return flagLogic.queryApproveFlagList(requestDto.getCondition());
    }

}
