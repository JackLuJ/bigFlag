package com.jackluan.bigflag.domain.flag.dto.request;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.domain.flag.dto.base.ApproverBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:43
 */
@Data
@NoArgsConstructor
public class ApproverRequestDto extends ApproverBaseDto {

    private Long flagUserId;

    private YesNoEnum addApprover;

    private List<ApproverStatusEnum> statusList;

}
