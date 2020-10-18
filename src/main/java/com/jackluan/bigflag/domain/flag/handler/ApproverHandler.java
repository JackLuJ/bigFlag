package com.jackluan.bigflag.domain.flag.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import com.jackluan.bigflag.domain.flag.convert.ApproverConvert;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.logic.ApproverLogic;
import com.jackluan.bigflag.domain.flag.logic.FlagLogic;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/10 22:16
 */
@Component
public class ApproverHandler {

    @Autowired
    private ApproverLogic approverLogic;

    @Autowired
    private FlagLogic flagLogic;

    public ResultBase<ApproverResponseDto> createApprover(ApproverRequestDto approverRequestDto) {
        ApproverRequestDto countRequest = new ApproverRequestDto();
        countRequest.setFlagId(approverRequestDto.getFlagId());
        countRequest.setUserId(approverRequestDto.getUserId());
        List<ApproverResponseDto> queryApproverList = approverLogic.queryApproverList(countRequest);
        if (!CollectionUtils.isEmpty(queryApproverList)) {
            ApproverResponseDto approverResponseDto = queryApproverList.get(0);
            if (ApproverStatusEnum.UNCONFIRMED == approverResponseDto.getStatus() || ApproverStatusEnum.EFFECTIVE == approverResponseDto.getStatus()) {
                return new ResultBase<ApproverResponseDto>().failed(ResultCodeConstant.HAS_BEAN_APPROVER);
            }else{
                ApproverRequestDto requestDto = new ApproverRequestDto();
                requestDto.setId(approverResponseDto.getId());
                requestDto.setStatus(ApproverStatusEnum.UNCONFIRMED);
                approverLogic.updateApprover(requestDto);
                return new ResultBase<ApproverResponseDto>().success(approverResponseDto);
            }

        }

        long approverId = approverLogic.createApprover(approverRequestDto);
        if (approverId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_APPROVER_TRACE_FAILED);
        }
        ApproverResponseDto responseDto = new ApproverResponseDto();
        responseDto.setId(approverId);
        return new ResultBase<ApproverResponseDto>().success(responseDto);
    }

    public ResultBase<ApproverResponseDto> confirmApprover(ApproverRequestDto approverRequestDto) {
        ApproverRequestDto requestDto = new ApproverRequestDto();
        requestDto.setId(approverRequestDto.getId());
        requestDto.setStatus(ApproverStatusEnum.UNCONFIRMED);
        List<ApproverResponseDto> approverList = approverLogic.queryApproverList(requestDto);
        if (CollectionUtils.isEmpty(approverList)) {
            return new ResultBase<ApproverResponseDto>().failed(ResultCodeConstant.APPROVER_NOT_EXITS);
        }
        FlagRequestDto flagRequestDto = new FlagRequestDto();
        flagRequestDto.setId(approverList.get(0).getFlagId());
        flagRequestDto.setUserId(approverRequestDto.getFlagUserId());
        List<FlagResponseDto> flagList = flagLogic.queryFlagList(flagRequestDto);
        if (CollectionUtils.isEmpty(flagList)) {
            return new ResultBase<ApproverResponseDto>().failed(ResultCodeConstant.HAVE_NOT_APPROVE_PERMISSION);
        }

        int count = approverLogic.updateApprover(approverRequestDto);
        if (count < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_APPROVER_FAILED);
        }
        ApproverResponseDto responseDto = new ApproverResponseDto();
        responseDto.setFlagId(approverList.get(0).getFlagId());
        responseDto.setUserId(approverList.get(0).getUserId());
        return new ResultBase<ApproverResponseDto>().success(responseDto);
    }

    public ResultBase<Page<ApproverResponseDto>> queryPage(Page<ApproverRequestDto> approverRequestDtoPage) {
        int count = approverLogic.queryCountExtra(approverRequestDtoPage.getPageCondition());
        approverRequestDtoPage.setTotal(count);
        List<ApproverResponseDto> responseList = approverLogic.queryApproverListExtra(approverRequestDtoPage.getCondition());

        Page<ApproverResponseDto> responseDtoPage = Page.mapper(approverRequestDtoPage);
        responseDtoPage.setResults(responseList);
        return new ResultBase<Page<ApproverResponseDto>>().success(responseDtoPage);
    }

    public ResultBase<List<ApproverResponseDto>> queryList(ApproverRequestDto approverRequestDto) {
        return new ResultBase<List<ApproverResponseDto>>().success(approverLogic.queryApproverList(approverRequestDto));
    }

    public ResultBase<Boolean> queryUnderApproveCount(ApproverRequestDto approverRequestDto) {
        approverRequestDto.setStatus(ApproverStatusEnum.UNCONFIRMED);
        int count = approverLogic.queryCount(approverRequestDto);
        if (count > 0) {
            return new ResultBase<Boolean>().success(true);
        } else {
            return new ResultBase<Boolean>().success(false);
        }
    }

}
