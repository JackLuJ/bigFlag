package com.jackluan.bigflag.domain.sign.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import com.jackluan.bigflag.common.utils.DateUtils;
import com.jackluan.bigflag.domain.sign.convert.SignConvert;
import com.jackluan.bigflag.domain.sign.convert.SignTraceConvert;
import com.jackluan.bigflag.domain.sign.dto.base.SignApproverBaseDto;
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

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author: jeffery.luan
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

        String approverInfo = null;
        if (CollectionUtils.isEmpty(signRequestDto.getApproverList())) {
            signRequestDto.setStatus(SignStatusEnum.PASSED);
            signRequestDto.setAchieveDate(DateUtils.now());
        } else {
            signRequestDto.setStatus(SignStatusEnum.UNDER_REVIEW);
            List<Long> approverUserId = signRequestDto.getApproverList().stream().map(SignApproverBaseDto::getApproverUserId).collect(Collectors.toList());
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
        signResponseDto.setId(signId);
        signResponseDto.setPassCount(signRequestDto.getStatus() == SignStatusEnum.PASSED ? 1 : 0);
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
                List<SignApproverResponseDto> approverList = signApproverLogic.queryList(signApproverRequestDto);
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

    public ResultBase<SignResponseDto> updateSign(SignRequestDto signRequestDto) {
        if (signRequestDto.getChangeType() == null || signRequestDto.getDelApprover() == null) {
            return new ResultBase<SignResponseDto>().success();
        }

        SignRequestDto requestDto = new SignRequestDto();
        requestDto.setFlagId(signRequestDto.getFlagId());
        requestDto.setStatus(SignStatusEnum.UNDER_REVIEW);
        List<SignResponseDto> responseList = signLogic.querySignList(requestDto);
        if (CollectionUtils.isEmpty(responseList)) {
            return new ResultBase<SignResponseDto>().success();
        }

        AtomicInteger passCount = new AtomicInteger();

        responseList.forEach(signResponseDto -> {
            SignTraceRequestDto signTraceRequestDto = SignTraceConvert.INSTANCE.convert(signResponseDto);
            signTraceRequestDto.setSequenceNo(signTraceRequestDto.getSequenceNo() + 1);
            switch (signRequestDto.getChangeType()) {
                case APPROVER:
                    SignApproverRequestDto approverRequestDto = signRequestDto.getDelApprover();
                    approverRequestDto.setSignId(signResponseDto.getId());
                    if (approverRequestDto.getApproverId() == null) {
                        throw new BigFlagRuntimeException(ResultCodeConstant.CAN_NOT_FIND_SIGN_APPROVER);
                    }
                    List<SignApproverResponseDto> signApproverResponse = signApproverLogic.queryList(approverRequestDto);
                    if (CollectionUtils.isEmpty(signApproverResponse) || signApproverResponse.size() != 1) {
                        return;
                    }
                    int delScore = signApproverResponse.get(0).getScore();
                    int count = signApproverLogic.delete(approverRequestDto);
                    if (count != 1) {
                        throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_SIGN_APPROVER_FAILED);
                    }

                    SignApproverRequestDto approverRequest = new SignApproverRequestDto();
                    approverRequest.setSignId(signTraceRequestDto.getSignId());
                    List<SignApproverResponseDto> exist = signApproverLogic.queryList(approverRequest);
                    if (CollectionUtils.isEmpty(exist)) {
                        signTraceRequestDto.setStatus(SignStatusEnum.PASSED);
                        signTraceRequestDto.setThreshold(0);
                        passCount.getAndIncrement();
                    } else {
                        int approveScore = exist.stream().filter(approver -> approver.getResultType() == SignApproverResultEnum.APPROVE).mapToInt(SignApproverBaseDto::getScore).sum();
                        //更新threshold
                        int newThreshold = signTraceRequestDto.getThreshold() - delScore;
                        signTraceRequestDto.setThreshold(Math.max(newThreshold, 1));

                        //如果达成更新状态
                        if (signTraceRequestDto.getThreshold() <= approveScore) {
                            signTraceRequestDto.setStatus(SignStatusEnum.PASSED);
                            passCount.getAndIncrement();
                        }

                        //更新approverInfo
                        List<Long> userIds = exist.stream().map(SignApproverBaseDto::getApproverUserId).collect(Collectors.toList());
                        JsonConverter converter = new JsonConverter();
                        signTraceRequestDto.setApproverInfo(converter.objToJson(userIds));
                    }
                    break;
                case TERMINATION:
                    signTraceRequestDto.setStatus(SignStatusEnum.TERMINATION);
                    break;
                default:
                    break;
            }

            long traceCount = signTraceLogic.createSignTrace(signTraceRequestDto);
            if (traceCount < 1) {
                throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_SIGN_TRACE_FAILED);
            }

            SignRequestDto newSignRequest = SignConvert.INSTANCE.convert(signTraceRequestDto);
            int count = signLogic.update(newSignRequest);
            if (count < 1) {
                throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_SIGN_FAILED);
            }

        });

        SignResponseDto responseDto = new SignResponseDto();
        responseDto.setPassCount(passCount.intValue());
        return new ResultBase<SignResponseDto>().success(responseDto);
    }

    public ResultBase<SignResponseDto> approveSign(SignApproverRequestDto signApproverRequestDto) {
        SignResponseDto signResponseDto = new SignResponseDto();

        //校验审批
        SignApproverRequestDto queryRequest = new SignApproverRequestDto();
        queryRequest.setSignId(signApproverRequestDto.getSignId());
        queryRequest.setApproverUserId(signApproverRequestDto.getApproverUserId());
        queryRequest.setResultType(SignApproverResultEnum.UN_CONFIRM);
        List<SignApproverResponseDto> signApproverList = signApproverLogic.queryList(queryRequest);
        if (CollectionUtils.isEmpty(signApproverList)) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CAN_NOT_APPROVE_SIGN);
        }

        //更新signApprover
        signApproverRequestDto.setId(signApproverList.get(0).getId());
        int count = signApproverLogic.update(signApproverRequestDto);
        if (count < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.APPROVE_SIGN_FAILED);
        }

        //校验sign是否通过
        SignApproverRequestDto sumRequest = new SignApproverRequestDto();
        sumRequest.setSignId(signApproverRequestDto.getSignId());
        List<SignApproverResponseDto> approverList = signApproverLogic.queryList(sumRequest);
        AtomicReference<Boolean> allApprove = new AtomicReference<>(true);
        AtomicInteger sumScore = new AtomicInteger();
        approverList.forEach(approver -> {
            if (approver.getResultType() == SignApproverResultEnum.UN_CONFIRM) {
                allApprove.set(false);
            } else if ((approver.getResultType() == SignApproverResultEnum.APPROVE)) {
                sumScore.getAndAdd(approver.getScore());
            }
        });

        SignRequestDto signRequestDto = new SignRequestDto();
        signRequestDto.setId(signApproverRequestDto.getSignId());
        List<SignResponseDto> signList = signLogic.querySignList(signRequestDto);
        if (CollectionUtils.isEmpty(signList)) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CAN_NOT_FIND_SIGN);
        }

        int achieveTimes = this.queryTodayAchieveTimes(signList.get(0).getFlagId(), DateUtils.now());
        signResponseDto.setAlreadyAchieveTimes(achieveTimes);
        signResponseDto.setFlagId(signList.get(0).getFlagId());
        signResponseDto.setUserId(signList.get(0).getUserId());

        boolean statusChange = false;
        if (signList.get(0).getThreshold() <= sumScore.get()) {
            signResponseDto.setPassCount(1);
            statusChange = true;
            signRequestDto.setStatus(SignStatusEnum.PASSED);
            signRequestDto.setAchieveDate(DateUtils.now());
        } else if (allApprove.get()) {
            statusChange = true;
            signRequestDto.setStatus(SignStatusEnum.NO_PASS);
        }

        //sign状态变更
        if (statusChange) {
            signLogic.update(signRequestDto);
        }

        signResponseDto.setStatus(signRequestDto.getStatus() == null ? signList.get(0).getStatus() : signRequestDto.getStatus());
        signResponseDto.setSignFinish(statusChange);

        return new ResultBase<SignResponseDto>().success(signResponseDto);
    }

    public List<SignResponseDto> queryListByTimeRange(SignRequestDto signRequestDto) {
        return signLogic.selectSignListWithDate(signRequestDto);
    }

    public int queryTodayAchieveTimes(Long flagId, Date date) {
        SignRequestDto checkRequestDto = new SignRequestDto();
        checkRequestDto.setFlagId(flagId);
        checkRequestDto.setStatus(SignStatusEnum.PASSED);
        checkRequestDto.setStartTime(DateUtils.getDayStart(date, 0));
        checkRequestDto.setEndTime(DateUtils.getDayEnd(date));
        return signLogic.selectSignCountWithDate(checkRequestDto);
    }

    public void delete(SignRequestDto signRequestDto) {
        signLogic.delete(signRequestDto);
    }

    public List<SignResponseDto> queryList(SignRequestDto signRequestDto) {
        return signLogic.querySignList(signRequestDto);
    }
}
