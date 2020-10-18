package com.jackluan.bigflag.provider.dto.request.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/5 23:40
 */
@Data
@NoArgsConstructor
public class WeChatMsgDataParam {

    private String value;

    private String color;
}
