package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.share.dto.request.SendOaMessageRequestDto;
import com.jackluan.bigflag.share.dto.request.wechat.WeChatMsgParam;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 0:16
 */
public interface IWeChatNoticeService {

    void sendMsg(SendOaMessageRequestDto sendOaMessageRequestDto);

}
