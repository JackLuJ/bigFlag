package com.jackluan.bigflag.domain.flag.dto.base;

import com.jackluan.bigflag.common.base.BaseDo;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.common.enums.flag.ApproverTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:43
 */
@Data
@NoArgsConstructor
public class ApproverBaseDto extends BaseDto {

    private Long id;

    private Long userId;

    private Long flagId;

    private Integer score;

    private ApproverTypeEnum approverType;

    private ApproverStatusEnum status;
}
