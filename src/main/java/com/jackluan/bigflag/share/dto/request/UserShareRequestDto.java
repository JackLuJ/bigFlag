package com.jackluan.bigflag.share.dto.request;

import com.jackluan.bigflag.common.enums.user.UserStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 22:59
 */
@Data
@NoArgsConstructor
public class UserShareRequestDto {

    private Long id;

    private String nickname;

    private List<String> fileUniqueCodes;

    private String mobile;

}
