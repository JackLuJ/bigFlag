package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.LoginShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.LoginShareResponseDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 20:18
 */
public interface ILoginService {

    ResultBase<LoginShareResponseDto> login(LoginShareRequestDto loginShareRequestDto);

    ResultBase<LoginShareResponseDto> refreshToken(LoginShareRequestDto loginShareRequestDto);

    String validate(String token, String signature, String timestamp, String nonce, String echostr);

    String processMsg(HttpServletRequest request);

    String processAppMsg(HttpServletRequest request);

}
