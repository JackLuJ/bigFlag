package com.jackluan.bigflag.provider.dto.request.notice;

import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 20:37
 */
@Data
@NoArgsConstructor
public class NoticeSignFinishRequestDto {

    private Long userId;

    private Long approverId;

    private Long flagId;

    private Long signId;

    private SignApproverResultEnum resultType;

    private SignStatusEnum status;

}
