package com.jackluan.bigflag.common.utils;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.constant.CacheObject;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.constant.WeChatConstant;
import com.jackluan.bigflag.provider.dto.response.wechat.AccessTokenResponseDto;
import com.jackluan.bigflag.provider.dto.response.wechat.SecCheckResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/5 23:37
 */
@Slf4j
@Component
public class WeChatUtils {
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

    @Value("${we-chat.app.img-sec-check-url}")
    private String imgSecCheckUrl;

    @Value("${we-chat.app.msg-sec-check-url}")
    private String msgSecCheckUrl;

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

    public SecCheckResponseDto imgSecCheck(byte[] bytes, String fileName) {
        String url = String.format(imgSecCheckUrl, this.getAccessToken(WeChatConstant.APP));
        String response = HttpUtils.post(url, bytes, fileName);
        log.info("check img weChat response:{}", response);
        JsonConverter converter = new JsonConverter();
        return converter.jsonToObj(response, SecCheckResponseDto.class);
    }

    public SecCheckResponseDto msgSecCheck(String content) {
        JsonConverter converter = new JsonConverter();
        Map<String, String> param = new HashMap<>(4);
        param.put("content", content);
        String json = converter.objToJson(param);
        String url = String.format(msgSecCheckUrl, this.getAccessToken(WeChatConstant.APP));
        String response = HttpUtils.post(url, json);
        log.info("check msg weChat request param:{} response:{}", json, response);
        return converter.jsonToObj(response, SecCheckResponseDto.class);
    }

}
