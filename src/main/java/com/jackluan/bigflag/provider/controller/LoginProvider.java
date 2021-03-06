package com.jackluan.bigflag.provider.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.annotation.PassToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.share.ILoginShareService;
import com.jackluan.bigflag.provider.dto.request.LoginShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.LoginShareResponseDto;
import com.jackluan.bigflag.provider.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/22 20:18
 */
@Slf4j
@CheckToken
@RestController
public class LoginProvider implements ILoginShareService {
    private JsonConverter converter = new JsonConverter();

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
    public String validate(String signature, String timestamp, String nonce, String echostr) {
        return loginService.validate(SystemConstant.WE_CHAT_OA_TOKEN, signature, timestamp, nonce, echostr);
    }

    @PassToken
    @Override
    public String processMsg(HttpServletRequest request) {
        return loginService.processMsg(request);
    }

    @PassToken
    @Override
    public String validateApp(String signature, String timestamp, String nonce, String echostr) {
        return loginService.validate(SystemConstant.WE_CHAT_APP_TOKEN, signature, timestamp, nonce, echostr);
    }

    @PassToken
    @Override
    public String processMsgApp(HttpServletRequest request) {
        return loginService.processAppMsg(request);
    }

    @PassToken
    @Override
    public ResultBase<String> test() {
        return new ResultBase<String>().success("hello world");
    }
}
