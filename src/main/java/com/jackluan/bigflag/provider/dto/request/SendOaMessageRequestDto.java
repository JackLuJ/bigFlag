package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.enums.base.WeChatNoticeTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 10:28
 */
@Data
@NoArgsConstructor
public class SendOaMessageRequestDto {

    private String oaOpenId;

    private WeChatNoticeTypeEnum msgType;

    private Map<String, String> paramMap;

    private Long userId;

    private Long noticeConfigId;

    private Map<String, String> pathParamMap;

}
