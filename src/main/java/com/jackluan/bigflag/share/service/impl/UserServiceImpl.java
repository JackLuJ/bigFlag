package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.share.convert.UserShareConvert;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.share.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:18
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserHandler userHandler;

    @Override
    public ResultBase<Void> createUser(UserShareRequestDto userShareRequestDto) {
        UserRequestDto requestDto = UserShareConvert.INSTANCE.convertToDomainDto(userShareRequestDto);
        try {
            return userHandler.createUserHandler(requestDto);
        } catch (BigFlagRuntimeException e) {
            return new ResultBase<Void>().failed(e);
        }
    }

}
