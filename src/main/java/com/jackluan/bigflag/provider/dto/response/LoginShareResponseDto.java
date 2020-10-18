package com.jackluan.bigflag.provider.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/22 15:56
 */
@Data
@NoArgsConstructor
public class LoginShareResponseDto implements Serializable {

    private String token;

}
