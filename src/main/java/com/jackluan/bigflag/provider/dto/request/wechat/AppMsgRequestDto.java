package com.jackluan.bigflag.provider.dto.request.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 17:19
 */
@Data
@NoArgsConstructor
public class AppMsgRequestDto {
    private String MsgType;

    private String AppId;
}
