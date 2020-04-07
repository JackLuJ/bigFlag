package com.jackluan.bigflag.share.dto.request;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/28 20:54
 */
@Data
@NoArgsConstructor
public class SignShareRequestDto extends BaseDto {

    private Long id;

    private Long userId;

    private Long flagId;

    private QueryTypeEnum queryType;
}
