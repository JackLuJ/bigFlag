package com.jackluan.bigflag.provider.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 21:21
 */
@Data
@NoArgsConstructor
public class UserOpinionShareRequestDto {

    private Long id;

    private Long userId;

    private String description;

    private String mobile;

    private String xwName;

    private String qqNo;

}
