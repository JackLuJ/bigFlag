package com.jackluan.bigflag.share.dto.request;

import com.jackluan.bigflag.common.enums.user.UserStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 22:59
 */
@Data
@NoArgsConstructor
public class UserShareRequestDto {

    private Long id;

    private String appOpenId;

    private String oaOpenId;

    private String uniqueId;

    private String mobile;

    private UserStatusEnum status;

}
