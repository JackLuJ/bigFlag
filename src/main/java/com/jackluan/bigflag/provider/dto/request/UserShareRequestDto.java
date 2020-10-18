package com.jackluan.bigflag.provider.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/3 22:59
 */
@Data
@NoArgsConstructor
public class UserShareRequestDto {

    private Long id;

    private String nickname;

    private String fileUniqueCode;

    private String mobile;

    private String encryptedData;

    private String iv;

}
