package com.jackluan.bigflag.domain.sign.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import com.jackluan.bigflag.common.utils.DateUtils;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignTraceDo;
import com.jackluan.bigflag.domain.sign.convert.SignTraceConvert;
import com.jackluan.bigflag.domain.sign.dto.base.SignBaseDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignTraceRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignApproverResponseDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import com.jackluan.bigflag.domain.sign.logic.SignApproverLogic;
import com.jackluan.bigflag.domain.sign.logic.SignLogic;
import com.jackluan.bigflag.domain.sign.logic.SignTraceLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:23
 */
@Component
public class SignHandler {

    @Autowired
    private SignLogic signLogic;

    @Autowired
    private SignTraceLogic signTraceLogic;

    @Autowired
    private SignApproverLogic signApproverLogic;

    public ResultBase<SignResponseDto> createSign(SignRequestDto signRequestDto) {

        if (signRequestDto.getCheckDailyTimes()) {
            SignRequestDto checkRequestDto = new SignRequestDto();
            checkRequestDto.setFlagId(signRequestDto.getFlagId());
            checkRequestDto.setStartTime(DateUtils.getTodayStart());
            checkRequestDto.setEndTime(DateUtils.getTodayEnd());
            int count = signLogic.selectSignCountWithDate(checkRequestDto);
            if (count > 0) {
                return new ResultBase<SignResponseDto>().failed(ResultCodeConstant.HAD_CREATE_SIGN_TODAY);
            }
        }

        String approverInfo = null;
        if (CollectionUtils.isEmpty(signRequestDto.getApproverList())) {
            signRequestDto.setStatus(SignStatusEnum.PASSED);
        } else {
            signRequestDto.setStatus(SignStatusEnum.UNDER_REVIEW);
            List<Long> approverUserId = new ArrayList<>();
            signRequestDto.getApproverList().forEach(approver -> {
                approverUserId.add(approver.getApproverUserId());
            });
            JsonConverter converter = new JsonConverter();
            approverInfo = converter.objToJson(approverUserId);
        }

        signRequestDto.setSequenceNo(1);
        long signId = signLogic.createSign(signRequestDto);
        if (signId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_SIGN_FAILED);
        }

        SignTraceRequestDto signTraceRequestDto = SignTraceConvert.INSTANCE.convert(signRequestDto);
        signTraceRequestDto.setSignId(signId);
        signTraceRequestDto.setApproverInfo(approverInfo);
        if (CollectionUtils.isEmpty(signRequestDto.getApproverList())) {
            signTraceRequestDto.setStatus(SignStatusEnum.PASSED);
        } else {
            List<Long> approverUserId = signRequestDto.getApproverList().stream().map(SignApproverRequestDto::getApproverUserId).collect(Collectors.toList());
            JsonConverter converter = new JsonConverter();
            signTraceRequestDto.setStatus(SignStatusEnum.UNDER_REVIEW);
            signTraceRequestDto.setApproverInfo(converter.objToJson(approverUserId));
        }
        long signTraceId = signTraceLogic.createSignTrace(signTraceRequestDto);
        if (signTraceId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_SIGN_TRACE_FAILED);
        }

        if (!CollectionUtils.isEmpty(signRequestDto.getApproverList())) {
            signRequestDto.setStatus(SignStatusEnum.UNDER_REVIEW);
            signRequestDto.getApproverList().forEach(approver -> {
                approver.setSignId(signId);
                approver.setResultType(SignApproverResultEnum.UN_CONFIRM);
                long signApproverId = signApproverLogic.createSignApprover(approver);
                if (signApproverId < 1) {
                    throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_SIGN_APPROVER_FAILED);

                }
            });
        }

        SignResponseDto signResponseDto = new SignResponseDto();
        signResponseDto.setStatus(signRequestDto.getStatus());
        signResponseDto.setId(signId);
        return new ResultBase<SignResponseDto>().success(signResponseDto);
    }

    public ResultBase<Page<SignResponseDto>> querySignListPage(Page<SignRequestDto> requestDto) {

        List<SignResponseDto> responseList;
        switch (requestDto.getCondition().getQueryType()) {
            case OWN:
                responseList = queryOwnSignList(requestDto);
                break;
            case APPROVE:
                responseList = queryApproveSignList(requestDto);
                break;
            default:
                return new ResultBase<Page<SignResponseDto>>().success();
        }

        if (!CollectionUtils.isEmpty(responseList)) {
            responseList.forEach(signResponseDto -> {
                SignApproverRequestDto signApproverRequestDto = new SignApproverRequestDto();
                signApproverRequestDto.setSignId(signResponseDto.getId());
                signApproverRequestDto.setResultType(SignApproverResultEnum.UN_CONFIRM);
                List<SignApproverResponseDto> approverList = signApproverLogic.selectByResultTypeNotIn(signApproverRequestDto);
                signResponseDto.setSignApproverList(approverList);
            });
        }

        Page<SignResponseDto> page = Page.mapper(requestDto);
        page.setResults(responseList);
        return new ResultBase<Page<SignResponseDto>>().success(page);
    }

    private List<SignResponseDto> queryOwnSignList(Page<SignRequestDto> requestDto) {
        int count = signLogic.count(requestDto.getPageCondition());
        requestDto.setTotal(count);
        return signLogic.querySignList(requestDto.getCondition());
    }

    private List<SignResponseDto> queryApproveSignList(Page<SignRequestDto> requestDto) {
        int count = signLogic.selectApproveSignCount(requestDto.getPageCondition());
        requestDto.setTotal(count);
        return signLogic.queryApproveSignList(requestDto.getCondition());
    }
}
