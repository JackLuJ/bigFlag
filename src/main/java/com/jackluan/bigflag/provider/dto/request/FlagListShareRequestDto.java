package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.annotation.NotEmpty;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/5/24 23:29
 */
@Data
@NoArgsConstructor
public class FlagListShareRequestDto extends BaseDto {

    private Long userId;

    @NotEmpty
    private QueryTypeEnum queryType;

    @NotEmpty
    private FlagStatusCategoryEnum flagStatusCategory;

}
