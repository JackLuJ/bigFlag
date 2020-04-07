package com.jackluan.bigflag.domain.sign.handler;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignApproverResponseDto;
import com.jackluan.bigflag.domain.sign.logic.SignApproverLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/4/3 15:40
 */
@Component
public class SignApproverHandler {

    @Autowired
    private SignApproverLogic signApproverLogic;

    public ResultBase<List<SignApproverResponseDto>> queryList(SignApproverRequestDto signApproverRequestDto){
        return new ResultBase<List<SignApproverResponseDto>>().success(signApproverLogic.queryList(signApproverRequestDto));
    }

}
