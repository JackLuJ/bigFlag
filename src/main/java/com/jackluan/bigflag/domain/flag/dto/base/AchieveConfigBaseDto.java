package com.jackluan.bigflag.domain.flag.dto.base;

import com.jackluan.bigflag.common.base.BaseDo;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.AchieveWayTypeEnum;
import com.jackluan.bigflag.common.enums.flag.ThresholdCalculationTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 17:46
 */
@Data
@NoArgsConstructor
public class AchieveConfigBaseDto extends BaseDto {

    private Long id;

    private AchieveWayTypeEnum achieveWayType;

    private ThresholdCalculationTypeEnum thresholdCalculationType;

}
