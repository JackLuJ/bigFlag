package com.jackluan.bigflag.share.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.flag.AchieveConfigEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/11 23:29
 */
@Data
@NoArgsConstructor
public class FlagShareRequestDto extends BaseDto {

    private Long userId;

    private AchieveConfigEnum achieveConfigType;

    private String title;

    private String description;

    private FlagTypeEnum flagType;

    private String threshold;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = SystemConstant.TIMEZONE)
    private Date deadline;

}
