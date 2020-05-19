package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/12 20:29
 */
@Data
@NoArgsConstructor
public class QueryApproverShareRequestDto extends BaseDto {

    private Long flagId;

    private ApproverStatusEnum status;

    private Long userId;

    private List<ApproverStatusEnum> statusList;

}
