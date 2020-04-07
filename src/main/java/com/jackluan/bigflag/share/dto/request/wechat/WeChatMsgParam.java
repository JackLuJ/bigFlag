package com.jackluan.bigflag.share.dto.request.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: jack.luan
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
