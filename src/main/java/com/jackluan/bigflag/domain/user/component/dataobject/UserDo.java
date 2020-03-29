package com.jackluan.bigflag.domain.user.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 16:57
 */
@Data
@NoArgsConstructor
public class UserDo extends BaseDo {

    private Long id;

    private String appOpenId;

    private String oaOpenId;

    private String unionId;

    private String sessionKey;

    private String token;

    private Integer tokenDays;

    private String nickname;

    private Long fileGroupId;

    private String mobile;

    private Integer status;

}
