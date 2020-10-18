package com.jackluan.bigflag.provider.dto.request.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/5 23:25
 */
@Data
@NoArgsConstructor
public class WeChatMsgParam {

    private String touser;

    private String template_id;

    private WeChatMsgMiniProgram miniprogram;

    private Map<String, WeChatMsgDataParam> data;
}
