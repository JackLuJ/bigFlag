package com.jackluan.bigflag.domain.sign.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignTraceDo;
import com.jackluan.bigflag.domain.sign.convert.SignTraceConvert;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignTraceRequestDto;
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

        String approverInfo = null;
        if (CollectionUtils.isEmpty(signRequestDto.getApproverList())) {
            signRequestDto.setStatus(SignStatusEnum.PASSED);
        } else {
            signRequestDto.setStatus(SignStatusEnum.UNDER_REVIEW);
            List<Long> approverUserId = new ArrayList<>();
            signRequestDto.getApproverList().forEach(approver -> {
                approverUserId.add(approver.getApproverUserId());
                long signApproverId = signApproverLogic.createSignApprover(approver);
                if (signApproverId < 1){
                    throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_SIGN_APPROVER_FAILED);

                }
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

        SignResponseDto signResponseDto = new SignResponseDto();
        signResponseDto.setStatus(signRequestDto.getStatus());
        signResponseDto.setId(signId);
        return new ResultBase<SignResponseDto>().success(signResponseDto);
    }

}
