package com.jackluan.bigflag.share.dto.request.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 17:19
 */
@Data
@NoArgsConstructor
public class AppMsgRequestDto {
    private String MsgType;

    private String AppId;
}
