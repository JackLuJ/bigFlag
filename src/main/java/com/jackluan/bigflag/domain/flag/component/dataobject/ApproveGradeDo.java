package com.jackluan.bigflag.domain.flag.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:45
 */
@Data
@NoArgsConstructor
public class ApproveGradeDo extends BaseDo {

    private Long id;

    private String score;
}
