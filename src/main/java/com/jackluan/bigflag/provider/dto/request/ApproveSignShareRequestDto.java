package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.annotation.NotEmpty;
import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/3 16:10
 */
@Data
@NoArgsConstructor
public class ApproveSignShareRequestDto {

    private Long userId;

    @NotEmpty
    private Long signId;

    @NotEmpty
    private SignApproverResultEnum resultType;

}
