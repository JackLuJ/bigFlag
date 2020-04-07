package com.jackluan.bigflag.share.dto.request.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/5 23:26
 */
@Data
@NoArgsConstructor
public class WeChatMsgMiniProgram {

    private String appid;

    private String pagepath;
}
