package com.jackluan.bigflag.provider.dto.response;

import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/3 16:44
 */
@Data
@NoArgsConstructor
public class SignApproverShareResponseDto extends UserInfoShareResponseDto {

    private SignApproverResultEnum resultType;

}
