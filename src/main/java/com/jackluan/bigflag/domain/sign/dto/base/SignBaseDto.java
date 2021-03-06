package com.jackluan.bigflag.domain.sign.dto.base;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 22:04
 */
@Data
@NoArgsConstructor
public class SignBaseDto extends BaseDto {

    private Long id;

    private Long userId;

    private Long flagId;

    private String description;

    private Long fileGroupId;

    private Integer threshold;

    private Date achieveDate;

    private Date deadline;

    private SignStatusEnum status;

    private Integer sequenceNo;

}
