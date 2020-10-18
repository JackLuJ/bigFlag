package com.jackluan.bigflag.domain.user.dto.base;

import com.jackluan.bigflag.common.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 17:02
 */
@Data
@NoArgsConstructor
public class UserOpinionBaseDto extends BaseDto {

    private Long id;

    private Long userId;

    private String description;

    private String mobile;

    private String xwName;

    private String qqNo;

}
