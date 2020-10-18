package com.jackluan.bigflag.provider.dto.response.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 10:52
 */
@Data
@NoArgsConstructor
public class SendOaMsgResponseDto {

    private Integer errcode;

    private String errmsg;

    private Long msgid;

}
