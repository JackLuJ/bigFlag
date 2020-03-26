package com.jackluan.bigflag.domain.flag.dto.request;

import com.jackluan.bigflag.common.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/17 22:28
 */
@Data
@NoArgsConstructor
public class CreateSingInfoRequestDto extends BaseDto {

    private Long userId;

    private Long flagId;

}
