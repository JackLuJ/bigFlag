package com.jackluan.bigflag.share.dto.request.notice;

import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 20:37
 */
@Data
@NoArgsConstructor
public class NoticeSignFinishRequestDto {

    private Long userId;

    private Long approverId;

    private Long flagId;

    private SignApproverResultEnum resultType;

    private SignStatusEnum status;

}
