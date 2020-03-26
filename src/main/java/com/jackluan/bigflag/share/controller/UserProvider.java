package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.share.IUserShareService;
import com.jackluan.bigflag.share.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 19:57
 */
@CheckToken
@RestController
public class UserProvider implements IUserShareService {

    @Autowired
    private IUserService userService;

    @Override
    public ResultBase<Void> createUser(UserShareRequestDto userShareRequestDto) {
        return userService.createUser(userShareRequestDto);
    }

    @Override
    public ResultBase<Void> updateUser(UserShareRequestDto userShareRequestDto) {
        return userService.updateUser(userShareRequestDto);
    }
}
