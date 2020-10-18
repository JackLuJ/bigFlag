package com.jackluan.bigflag.domain.flag.dto.response;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/17 22:26
 */
@Data
@NoArgsConstructor
public class CreateSingInfoResponseDto extends BaseDto {

    private Integer threshold;

    private Date deadline;

    private List<ApproverResponseDto> approverList;

    private FlagTypeEnum flagType;

}
