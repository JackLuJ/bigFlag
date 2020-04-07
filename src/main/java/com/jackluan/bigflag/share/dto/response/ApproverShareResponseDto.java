package com.jackluan.bigflag.share.dto.response;

import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/30 15:28
 */
@Data
@NoArgsConstructor
public class ApproverShareResponseDto extends UserInfoShareResponseDto{

    private Long approverId;

    private Long flagId;

    private ApproverStatusEnum status;

    private Date createDate;

}
