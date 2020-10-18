package com.jackluan.bigflag.provider.dto.response.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/8 12:53
 */
@Data
@NoArgsConstructor
public class SecretInfoResponseDto {

    private String openId;

    private String nickName;

    private Integer gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String unionId;

    private WatermarkResponseDto watermark;

    @Data
    private static class WatermarkResponseDto{
        private Long timestamp;

        private String appid;
    }

}


