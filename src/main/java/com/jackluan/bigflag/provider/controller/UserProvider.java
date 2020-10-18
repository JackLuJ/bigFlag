package com.jackluan.bigflag.provider.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.provider.dto.request.UserOpinionShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.share.IUserShareService;
import com.jackluan.bigflag.provider.dto.response.UserInfoShareResponseDto;
import com.jackluan.bigflag.provider.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/3 19:57
 */
@Slf4j
@CheckToken
@RestController
public class UserProvider implements IUserShareService {
    private JsonConverter converter = new JsonConverter();

    @Autowired
    private IUserService userService;

    @Override
    public ResultBase<Void> createUser(UserShareRequestDto userShareRequestDto) {
        return userService.createUser(userShareRequestDto);
    }

    @Override
    public ResultBase<UserInfoShareResponseDto> updateUser(UserShareRequestDto userShareRequestDto) {
        return userService.updateUser(userShareRequestDto);
    }

    @Override
    public ResultBase<UserInfoShareResponseDto> queryUser() {
        UserShareRequestDto userShareRequestDto = new UserShareRequestDto();
        userShareRequestDto.setId(UserUtils.getUser().getUserId());
        return userService.queryUser(userShareRequestDto);
    }

    @Override
    public ResultBase<Void> opinion(UserOpinionShareRequestDto userOpinionShareRequestDto) {
        return userService.opinion(userOpinionShareRequestDto);
    }
}
