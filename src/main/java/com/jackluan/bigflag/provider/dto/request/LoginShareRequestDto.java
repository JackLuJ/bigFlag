package com.jackluan.bigflag.provider.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/22 15:59
 */
@Data
@NoArgsConstructor
public class LoginShareRequestDto {

    private String code;

    private String token;
}
