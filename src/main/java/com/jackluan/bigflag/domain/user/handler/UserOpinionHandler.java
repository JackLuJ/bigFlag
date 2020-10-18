package com.jackluan.bigflag.domain.user.handler;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.user.dto.request.UserOpinionRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserOpinionResponseDto;
import com.jackluan.bigflag.domain.user.logic.UserOpinionLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 21:12
 */
@Component
public class UserOpinionHandler {

    @Autowired
    private UserOpinionLogic userOpinionLogic;

    public ResultBase<UserOpinionResponseDto> create(UserOpinionRequestDto requestDto){
        long id = userOpinionLogic.create(requestDto);
        if (id < 1){
            return new ResultBase<UserOpinionResponseDto>().failed(ResultCodeConstant.CREATE_USER_OPINION_FAILED);
        }
        UserOpinionResponseDto responseDto = new UserOpinionResponseDto();
        responseDto.setId(id);
        return new ResultBase<UserOpinionResponseDto>().success(responseDto);
    }
}
