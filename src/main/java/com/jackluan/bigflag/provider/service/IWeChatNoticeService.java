package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.provider.dto.request.SendOaMessageRequestDto;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 0:16
 */
public interface IWeChatNoticeService {

    void sendMsg(SendOaMessageRequestDto sendOaMessageRequestDto);

}
