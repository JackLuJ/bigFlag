package com.jackluan.bigflag.share.dto.request;

import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.common.enums.flag.OperateApproverTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/3 4:50
 */
@Data
@NoArgsConstructor
public class ApproverOperateShareRequestDto {

    private Long userId;

    private Long flagId;

    private OperateApproverTypeEnum operate;

}
