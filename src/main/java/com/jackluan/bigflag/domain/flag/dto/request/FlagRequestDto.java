package com.jackluan.bigflag.domain.flag.dto.request;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import com.jackluan.bigflag.common.enums.flag.*;
import com.jackluan.bigflag.domain.flag.dto.base.FlagBaseDto;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:41
 */
@Data
@NoArgsConstructor
public class FlagRequestDto extends FlagBaseDto {

    private Boolean showApprover;

    private FlagUpdateTypeEnum flagUpdateType;

    private QueryTypeEnum queryType;

    private List<ApproverRequestDto> approverList;

    private Integer passCount;

    private YesNoEnum achieve;

    private FlagStatusEnum flagStatus;

    private FlagCategoryEnum flagCategory;

    private List<FlagStatusEnum> statusList;
}
