package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.CacheObject;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.constant.WeChatConstant;
import com.jackluan.bigflag.common.enums.user.OaSubscribeStatusEnum;
import com.jackluan.bigflag.common.enums.user.UserStatusEnum;
import com.jackluan.bigflag.common.utils.CommonUtils;
import com.jackluan.bigflag.common.utils.HttpUtils;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.share.convert.UserShareConvert;
import com.jackluan.bigflag.share.dto.request.LoginShareRequestDto;
import com.jackluan.bigflag.share.dto.response.LoginShareResponseDto;
import com.jackluan.bigflag.share.dto.response.wechat.AccessTokenResponseDto;
import com.jackluan.bigflag.share.dto.response.wechat.Code2SessionResponseDto;
import com.jackluan.bigflag.share.dto.response.wechat.WeChatOaUserInfoDto;
import com.jackluan.bigflag.share.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 20:18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements ILoginService {

    @Value("${we-chat.access-token-url}")
    private String getTokenUrl;

    @Value("${we-chat.app.code2Session-url}")
    private String requestUrl;

    @Value("${we-chat.app.app-id}")
    private String appId;

    @Value("${we-chat.app.app-secret}")
    private String appSecret;

    @Value("${we-chat.app.send-msg-url}")
    private String appSendMsgUrl;

    @Value("${we-chat.oa.app-id}")
    private String oaAppId;

    @Value("${we-chat.oa.app-secret}")
    private String oaAppSecret;

    @Value("${we-chat.oa.get-unionId-url}")
    private String oaGetUserUrl;

    @Autowired
    private UserHandler userHandler;

    @Override
    public ResultBase<LoginShareResponseDto> login(LoginShareRequestDto loginShareRequestDto) {
        String url = String.format(requestUrl, appId, appSecret, loginShareRequestDto.getCode());
        String response = HttpUtils.get(url);
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
        userRequestDto.setOaSubscribeStatus(OaSubscribeStatusEnum.UN_SUBSCRIBE);
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
        if (!resultBase.isSuccess()) {
            return new ResultBase<LoginShareResponseDto>().failed(resultBase);
        }

        LoginShareResponseDto responseDto = new LoginShareResponseDto();
        responseDto.setToken(refreshToken);
        return new ResultBase<LoginShareResponseDto>().success(responseDto);
    }

    @Override
    public String validate(String token, String signature, String timestamp, String nonce, String echostr) {
        return CommonUtils.checkSignature(token, signature, timestamp, nonce) ? echostr : null;
    }

    @Override
    public String processMsg(HttpServletRequest request) {
        JsonConverter converter = new JsonConverter();
        // 调用parseXml方法解析请求消息
        Map<String, String> requestMap = CommonUtils.parseXml(request);
        log.info("weChat msg receive requestMap: {}", converter.objToJson(requestMap));
        // 消息类型
        String msgType = requestMap.get(WeChatConstant.MSG_TYPE);
        // 事件类型
        if (WeChatConstant.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
            String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
            String eventType = requestMap.get(WeChatConstant.EVENT);
            String accessToken = this.getAccessToken(WeChatConstant.OA);

            //获取unionId
            String queryInfoUrl = String.format(oaGetUserUrl, accessToken, openId);
            String response = HttpUtils.get(queryInfoUrl);
            log.info("query user info response:{}", response);
            WeChatOaUserInfoDto userInfo = converter.jsonToObj(response, WeChatOaUserInfoDto.class);

            UserRequestDto user = new UserRequestDto();
            user.setOaOpenId(userInfo.getOpenid());
            // 判断是关注还是取消关注
            if (WeChatConstant.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
                user.setUnionId(userInfo.getUnionid());
                user.setOaSubscribeStatus(OaSubscribeStatusEnum.SUBSCRIBE);
            } else if (WeChatConstant.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
                user.setOaSubscribeStatus(OaSubscribeStatusEnum.UN_SUBSCRIBE);
            }
            ResultBase<Void> resultBase = userHandler.createOaUserHandler(user);
            if (!resultBase.isSuccess()) {
                throw new BigFlagRuntimeException(resultBase.getCode());
            }
        }
        return "";
    }

    @Override
    public String processAppMsg(HttpServletRequest request) {
        JsonConverter converter = new JsonConverter();
        // 调用parseXml方法解析请求消息
        Map<String, String> requestMap = CommonUtils.parseXml(request);
        log.info("weChat app msg receive requestMap: {}", converter.objToJson(requestMap));
        if (StringUtils.isEmpty(requestMap.get(WeChatConstant.MSG_TYPE_NAME)) || StringUtils.isEmpty(requestMap.get(WeChatConstant.APP_ID_NAME))
                || StringUtils.isEmpty(requestMap.get(WeChatConstant.APP_FROM_USER_NAME))) {
            return "";
        }
        String jsonText = String.format(WeChatConstant.APP_TEXT_MSG_DATA_JSON, requestMap.get(WeChatConstant.APP_FROM_USER_NAME));
        String jsonPhoto = String.format(WeChatConstant.APP_PHOTO_MSG_DATA_JSON, requestMap.get(WeChatConstant.APP_FROM_USER_NAME));
        String accessToken = this.getAccessToken(WeChatConstant.APP);
        String url = String.format(appSendMsgUrl, accessToken);
        String responseText = HttpUtils.post(url, jsonText);
        String responsePhoto = HttpUtils.post(url, jsonPhoto);
        log.info("send app msg. text result:{}. photo result:{}", responseText, responsePhoto);

        return "";
    }

    @Override
    public String getAccessToken(String type) {
        String key = null;
        String appId = null;
        String secret = null;
        if (WeChatConstant.APP.equals(type)) {
            key = SystemConstant.WE_CHAT_APP_ACCESS_TOKEN_KEY;
            appId = this.appId;
            secret = this.appSecret;
        } else if (WeChatConstant.OA.equals(type)) {
            key = SystemConstant.WE_CHAT_OA_ACCESS_TOKEN_KEY;
            appId = this.oaAppId;
            secret = this.oaAppSecret;
        } else {
            return null;
        }

        JsonConverter converter = new JsonConverter();
        String accessToken = CacheObject.weChatCache.get(key);

        //获取AccessToken
        if (accessToken == null) {
            String url = String.format(getTokenUrl, appId, secret);
            String response = HttpUtils.get(url);
            AccessTokenResponseDto accessTokenResponse = converter.jsonToObj(response, AccessTokenResponseDto.class);
            if (accessTokenResponse.getAccess_token() == null) {
                throw new BigFlagRuntimeException(ResultCodeConstant.WE_CHAT_OA_GET_TOKEN_FAILED);
            }
            accessToken = accessTokenResponse.getAccess_token();
            //放入缓存
            CacheObject.weChatCache.put(key, accessTokenResponse.getAccess_token());
        }
        return accessToken;
    }

}
