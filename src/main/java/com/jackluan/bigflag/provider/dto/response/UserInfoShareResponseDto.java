package com.jackluan.bigflag.provider.dto.response;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.user.OaSubscribeStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/23 18:53
 */
@Data
@NoArgsConstructor
public class UserInfoShareResponseDto extends BaseDto {

    private Long userId;

    private String nickname;

    private String url;

    private OaSubscribeStatusEnum oaSubscribeStatus;
}
