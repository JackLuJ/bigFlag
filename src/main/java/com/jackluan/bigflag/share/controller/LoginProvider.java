package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.annotation.PassToken;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.ILoginShareService;
import com.jackluan.bigflag.share.dto.request.LoginShareRequestDto;
import com.jackluan.bigflag.share.dto.response.LoginShareResponseDto;
import com.jackluan.bigflag.share.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 20:18
 */
@CheckToken
@RestController
public class LoginProvider implements ILoginShareService {

    @Autowired
    private ILoginService loginService;

    @PassToken
    @Override
    public ResultBase<LoginShareResponseDto> login(LoginShareRequestDto loginShareRequestDto) {
        return loginService.login(loginShareRequestDto);
    }

    @PassToken
    @Override
    public ResultBase<LoginShareResponseDto> refreshToken(LoginShareRequestDto loginShareRequestDto) {
        return loginService.refreshToken(loginShareRequestDto);
    }

    @PassToken
    @Override
    public ResultBase<String> test() {
        return new ResultBase<String>().success("hello world");
    }
}
