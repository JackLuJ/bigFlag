package com.jackluan.bigflag.provider.service.impl;

import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.constant.WeChatConstant;
import com.jackluan.bigflag.common.enums.notice.NoticeResultEnum;
import com.jackluan.bigflag.common.enums.notice.NoticeTypeEnum;
import com.jackluan.bigflag.common.utils.HttpUtils;
import com.jackluan.bigflag.common.utils.WeChatUtils;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeTraceRequestDto;
import com.jackluan.bigflag.domain.notice.handler.NoticeTraceHandler;
import com.jackluan.bigflag.provider.dto.request.SendOaMessageRequestDto;
import com.jackluan.bigflag.provider.dto.request.wechat.WeChatMsgDataParam;
import com.jackluan.bigflag.provider.dto.request.wechat.WeChatMsgMiniProgram;
import com.jackluan.bigflag.provider.dto.request.wechat.WeChatMsgParam;
import com.jackluan.bigflag.provider.dto.response.wechat.SendOaMsgResponseDto;
import com.jackluan.bigflag.provider.service.ILoginService;
import com.jackluan.bigflag.provider.service.IWeChatNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 0:16
 */
@Slf4j
@Service
public class WeChatNoticeServiceImpl implements IWeChatNoticeService {

    @Value("${we-chat.app.app-id}")
    private String appId;

    @Value("${we-chat.oa.send-msg-url}")
    private String sendUrl;

    @Autowired
    private NoticeTraceHandler noticeTraceHandler;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private WeChatUtils weChatUtils;

    @Override
    public void sendMsg(SendOaMessageRequestDto sendOaMessageRequestDto) {

        if (StringUtils.isEmpty(sendOaMessageRequestDto.getOaOpenId())) {
            return;
        }

        JsonConverter converter = new JsonConverter();

        WeChatMsgParam weChatMsgParam = this.formatMsg(sendOaMessageRequestDto);
        String url = String.format(sendUrl, weChatUtils.getAccessToken(WeChatConstant.OA));
        String json = converter.objToJson(weChatMsgParam);
        String result = HttpUtils.post(url, json);
        log.info("send oa message request is :{}", json);
        log.info("send oa message result is :{}", result);
        SendOaMsgResponseDto responseDto = converter.jsonToObj(result, SendOaMsgResponseDto.class);

        NoticeTraceRequestDto noticeTrace = new NoticeTraceRequestDto();
        noticeTrace.setUserId(sendOaMessageRequestDto.getUserId());
        noticeTrace.setNoticeConfigId(sendOaMessageRequestDto.getNoticeConfigId());
        noticeTrace.setNoticeType(NoticeTypeEnum.WE_CHAT);
        noticeTrace.setMsgType(sendOaMessageRequestDto.getMsgType());
        noticeTrace.setNoticeResult(responseDto.getErrcode() == 0 ? NoticeResultEnum.SUCCESS : NoticeResultEnum.FAILED);
        noticeTrace.setExtra(String.valueOf(responseDto.getMsgid()));
        noticeTraceHandler.createNoticeTrace(noticeTrace);

    }

    public static void main(String[] args) {
        String json = "{\"errcode\":0,\"errmsg\":\"ok\",\"msgid\":1330009782538960896}";
        JsonConverter converter = new JsonConverter();

        SendOaMsgResponseDto responseDto = converter.jsonToObj(json, SendOaMsgResponseDto.class);
        System.out.println(responseDto);

    }
    private WeChatMsgParam formatMsg(SendOaMessageRequestDto sendOaMessageRequestDto) {
        WeChatMsgMiniProgram miniProgram = new WeChatMsgMiniProgram();
        miniProgram.setAppid(appId);
        String path = sendOaMessageRequestDto.getMsgType().getPath();
        if (!CollectionUtils.isEmpty(sendOaMessageRequestDto.getPathParamMap())) {
            StringSubstitutor sub = new StringSubstitutor(sendOaMessageRequestDto.getPathParamMap());
            path = sub.replace(path);
        }
        miniProgram.setPagepath(path);

        Map<String, WeChatMsgDataParam> dataParamMap = new HashMap<>(16);

        WeChatMsgParam weChatMsgParam = new WeChatMsgParam();
        weChatMsgParam.setTouser(sendOaMessageRequestDto.getOaOpenId());
        weChatMsgParam.setTemplate_id(sendOaMessageRequestDto.getMsgType().getTemplateId());
        weChatMsgParam.setMiniprogram(miniProgram);
        weChatMsgParam.setData(dataParamMap);

        sendOaMessageRequestDto.getParamMap().forEach((k, v) -> {
            WeChatMsgDataParam data = new WeChatMsgDataParam();
            data.setValue(v);
            data.setColor("#000000");
            dataParamMap.put(k, data);
        });
        return weChatMsgParam;
    }

}
