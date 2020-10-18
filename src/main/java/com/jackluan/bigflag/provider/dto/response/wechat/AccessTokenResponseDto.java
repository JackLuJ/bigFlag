package com.jackluan.bigflag.provider.dto.response.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/5 17:06
 */
@Data
@NoArgsConstructor
public class AccessTokenResponseDto {

    private String access_token;

    private String expires_in;

    private Integer errcode;

    private String errmsg;
}
