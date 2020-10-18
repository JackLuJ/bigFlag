package com.jackluan.bigflag.provider.dto.request.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/5 23:26
 */
@Data
@NoArgsConstructor
public class WeChatMsgMiniProgram {

    private String appid;

    private String pagepath;
}
