package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.enums.flag.ApproverTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:12
 */
@Data
@NoArgsConstructor
public class ApproverCreateShareRequestDto {

    private Long userId;

    private Long flagId;

    private ApproverTypeEnum approverType;

}
