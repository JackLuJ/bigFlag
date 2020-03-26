package com.jackluan.bigflag.domain.flag.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:43
 */
@Data
@NoArgsConstructor
public class ApproverDo extends BaseDo {

    private Long id;

    private Long userId;

    private Long flagId;

    private Integer score;

    private Integer approverType;

    private Integer status;
}
