package com.jackluan.bigflag.domain.flag.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import com.jackluan.bigflag.domain.flag.convert.ApproverConvert;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.logic.ApproverLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:16
 */
@Component
public class ApproverHandler {

    @Autowired
    private ApproverLogic approverLogic;

    public ResultBase<ApproverResponseDto> createApprover(ApproverRequestDto approverRequestDto){
        long approverId =  approverLogic.createApprover(approverRequestDto);
        if (approverId < 1){
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_APPROVER_TRACE_FAILED);
        }
        ApproverResponseDto responseDto = new ApproverResponseDto();
        responseDto.setId(approverId);
        return new ResultBase<ApproverResponseDto>().success(responseDto);
    }

}
