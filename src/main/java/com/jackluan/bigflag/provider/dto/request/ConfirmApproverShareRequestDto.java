package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/12 20:29
 */
@Data
@NoArgsConstructor
public class ConfirmApproverShareRequestDto {

    private Long approverId;

    private YesNoEnum decision;

    private Long flagUserId;

}
