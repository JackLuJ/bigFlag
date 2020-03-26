package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.user.UserStatusEnum;
import com.jackluan.bigflag.common.utils.CommonUtils;
import com.jackluan.bigflag.common.utils.HttpUtils;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.share.convert.UserShareConvert;
import com.jackluan.bigflag.share.dto.request.LoginShareRequestDto;
import com.jackluan.bigflag.share.dto.response.LoginShareResponseDto;
import com.jackluan.bigflag.share.dto.response.wechat.Code2SessionResponseDto;
import com.jackluan.bigflag.share.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 20:18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements ILoginService {

    @Value("${we-chat.code2Session-url}")
    private String requestUrl;

    @Value("${we-chat.app-id}")
    private String appId;

    @Value("${we-chat.app-secret}")
    private String appSecret;

    @Autowired
    private UserHandler userHandler;

    @Override
    public ResultBase<LoginShareResponseDto> login(LoginShareRequestDto loginShareRequestDto) {
        String url = String.format(requestUrl, appId, appSecret, loginShareRequestDto.getCode());
        String response = HttpUtils.get(url);
//        String response = "{\"session_key\":\"xm7fUzHoZE8gJ0mlTcAz\\/g==\",\"openid\":\"oMT4h5R3p2nPpMeWa68MiU1ofrFk\"}";
        log.info("call weChat get user info url:{}, response:{}", url, response);
        JsonConverter converter = new JsonConverter();
        Code2SessionResponseDto responseDto = converter.jsonToObj(response, Code2SessionResponseDto.class);
        if (!StringUtils.isEmpty(responseDto.getErrcode())) {
            return new ResultBase<LoginShareResponseDto>().failed(ResultCodeConstant.WE_CHAT_USER_REQUEST_FAILED);
        }

        String token = CommonUtils.toMD5(responseDto.getUserCode());
        UserRequestDto userRequestDto = UserShareConvert.INSTANCE.convertToDomainDto(responseDto);
        userRequestDto.setToken(token);
        userRequestDto.setTokenDays(1);
        userRequestDto.setStatus(UserStatusEnum.EFFECTIVE);
        ResultBase<Void> resultBase = userHandler.createUserHandler(userRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<LoginShareResponseDto>().failed(resultBase);
        }

        LoginShareResponseDto loginShareResponseDto = new LoginShareResponseDto();
        loginShareResponseDto.setToken(token);
        return new ResultBase<LoginShareResponseDto>().success(loginShareResponseDto);
    }

    @Override
    public ResultBase<LoginShareResponseDto> refreshToken(LoginShareRequestDto loginShareRequestDto) {
        String refreshToken = CommonUtils.toMD5(loginShareRequestDto.getToken() + System.currentTimeMillis());
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setToken(loginShareRequestDto.getToken());
        userRequestDto.setNewToken(refreshToken);
        ResultBase<Void> resultBase = userHandler.refreshToken(userRequestDto);
        if (!resultBase.isSuccess()){
            return new ResultBase<LoginShareResponseDto>().failed(resultBase);
        }

        LoginShareResponseDto responseDto = new LoginShareResponseDto();
        responseDto.setToken(refreshToken);
        return new ResultBase<LoginShareResponseDto>().success(responseDto);
    }
}
