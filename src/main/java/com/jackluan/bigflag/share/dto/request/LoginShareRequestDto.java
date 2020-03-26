package com.jackluan.bigflag.share.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 15:59
 */
@Data
@NoArgsConstructor
public class LoginShareRequestDto {

    private String code;

    private String token;
}
