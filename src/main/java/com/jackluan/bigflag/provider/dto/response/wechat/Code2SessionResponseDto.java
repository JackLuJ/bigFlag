package com.jackluan.bigflag.provider.dto.response.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/22 21:43
 */
@Data
@NoArgsConstructor
public class Code2SessionResponseDto implements Serializable {

    private String openid;

    private String session_key;

    private String unionid;

    private String errcode;

    private String errmsg;

    public String getUserCode() {
        return this.openid + this.session_key + System.currentTimeMillis();
    }

}
