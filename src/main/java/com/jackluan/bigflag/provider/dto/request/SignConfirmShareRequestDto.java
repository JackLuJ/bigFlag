package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/30 15:50
 */
@Data
@NoArgsConstructor
public class SignConfirmShareRequestDto {

    private YesNoEnum decision;

    private Long flagId;

}
