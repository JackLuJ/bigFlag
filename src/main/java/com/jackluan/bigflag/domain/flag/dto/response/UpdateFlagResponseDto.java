package com.jackluan.bigflag.domain.flag.dto.response;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.FlagChangeTypeEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/21 0:01
 */
@Data
@NoArgsConstructor
public class UpdateFlagResponseDto extends BaseDto {

    private FlagStatusEnum flagStatusEnum;

    private List<FlagChangeTypeEnum> changeTypes;

    private Boolean flagFinish;

    private Long noticeConfigId;

    private FlagTypeEnum flagType;

}
