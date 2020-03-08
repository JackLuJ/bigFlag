package com.jackluan.bigflag.domain.user.handler;

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

    public Long createUserHandler(UserRequestDto userRequestDto){
        UserDo requestDo = UserConvert.INSTANCE.convertToDo(userRequestDto);
        return userLogic.createUser(requestDo);
    }

}
