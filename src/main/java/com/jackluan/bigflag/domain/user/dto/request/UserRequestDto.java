package com.jackluan.bigflag.domain.user.dto.request;

import com.jackluan.bigflag.domain.user.dto.base.UserBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 17:31
 */
@Data
@NoArgsConstructor
public class UserRequestDto extends UserBaseDto {

    private String newToken;
}
