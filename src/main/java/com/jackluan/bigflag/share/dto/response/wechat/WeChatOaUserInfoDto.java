package com.jackluan.bigflag.share.dto.response.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/4/5 17:18
 */
@Data
@NoArgsConstructor
public class WeChatOaUserInfoDto {
    private Integer subscribe;
    private String openid;
    private String nickname;
    private Integer sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private Long subscribe_time;
    private String unionid;
    private String remark;
    private Integer groupid;
    private List tagid_list;
    private String subscribe_scene;
    private String qr_scene;
    private String qr_scene_str;
    private Integer errcode;
    private String errmsg;
}
