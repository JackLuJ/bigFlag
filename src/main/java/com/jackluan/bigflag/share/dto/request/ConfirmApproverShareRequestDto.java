package com.jackluan.bigflag.share.dto.request;

import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
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

}
