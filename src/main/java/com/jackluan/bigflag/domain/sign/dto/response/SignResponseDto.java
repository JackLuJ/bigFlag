package com.jackluan.bigflag.domain.sign.dto.response;

import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import com.jackluan.bigflag.domain.sign.dto.base.SignBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:19
 */
@Data
@NoArgsConstructor
public class SignResponseDto extends SignBaseDto {

    private SignApproverResultEnum resultType;

    private List<SignApproverResponseDto> signApproverList;

    private List<SignApproverResponseDto> approveList;

    private Integer passCount = 0;

    private Boolean signFinish;

    private Integer alreadyAchieveTimes;
}
