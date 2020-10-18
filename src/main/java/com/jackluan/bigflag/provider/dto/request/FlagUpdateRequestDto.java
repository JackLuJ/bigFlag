package com.jackluan.bigflag.provider.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import com.jackluan.bigflag.common.enums.flag.AchieveConfigEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jeffery.luan
 * @Date: 2020/5/18 11:21
 */
@Data
@NoArgsConstructor
public class FlagUpdateRequestDto {

    private Long userId;

    private Long flagId;

    private AchieveConfigEnum achieveConfigType;

    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = SystemConstant.TIMEZONE)
    private Date deadline;

    private String noticeDate;

    private YesNoEnum achieve;
}
