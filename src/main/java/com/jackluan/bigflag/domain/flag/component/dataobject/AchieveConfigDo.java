package com.jackluan.bigflag.domain.flag.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 17:46
 */
@Data
@NoArgsConstructor
public class AchieveConfigDo extends BaseDo {

    private Long id;

    private Integer achieveWayType;

    private Integer thresholdCalculationType;

}
