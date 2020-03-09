package com.jackluan.bigflag.domain.user.dto.base;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.user.UserStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 17:29
 */
@Data
@NoArgsConstructor
public class UserBaseDto extends BaseDto {

    private Long id;

    private String appOpenId;

    private String oaOpenId;

    private String uniqueId;

    private String mobile;

    private UserStatusEnum status;

}