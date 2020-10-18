package com.jackluan.bigflag.domain.sign.dto.base;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 22:05
 */
@Data
@NoArgsConstructor
public class SignApproverBaseDto extends BaseDto {

    private Long id;

    private Long signId;

    private Long approverId;

    private Long approverUserId;

    private Integer score;

    private SignApproverResultEnum resultType;

    private String description;

}
