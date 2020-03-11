package com.jackluan.bigflag.domain.user.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.user.component.dataobject.UserDo;
import com.jackluan.bigflag.domain.user.convert.UserConvert;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import com.jackluan.bigflag.domain.user.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:21
 */
@Component
public class UserHandler {

    @Autowired
    private UserLogic userLogic;

    public ResultBase<Void> createUserHandler(UserRequestDto userRequestDto){
        long id = userLogic.createUser(userRequestDto);
        if (id < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_USER_FAILED);
        }
        return new ResultBase<Void>().success();
    }

}
