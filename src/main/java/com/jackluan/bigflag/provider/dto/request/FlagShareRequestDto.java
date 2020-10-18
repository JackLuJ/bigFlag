package com.jackluan.bigflag.provider.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import com.jackluan.bigflag.common.enums.flag.AchieveConfigEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/11 23:29
 */
@Data
@NoArgsConstructor
public class FlagShareRequestDto extends BaseDto {

    private Long userId;

    private Long flagId;

    private AchieveConfigEnum achieveConfigType;

    private String title;

    private String description;

    private FlagTypeEnum flagType;

    private String threshold;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = SystemConstant.TIMEZONE)
    private Date deadline;

    private QueryTypeEnum queryType;

    private YesNoEnum terminateFlag;

    private String noticeDate;

    private FlagStatusEnum flagStatus;

}
