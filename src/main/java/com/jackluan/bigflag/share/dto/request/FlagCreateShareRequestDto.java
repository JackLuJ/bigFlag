package com.jackluan.bigflag.share.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.flag.AchieveConfigEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import com.jackluan.bigflag.common.enums.notice.FrequencyTypeEnum;
import com.jackluan.bigflag.common.enums.notice.NoticeTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:28
 */
@Data
@NoArgsConstructor
public class FlagCreateShareRequestDto {

    private Long userId;

    private AchieveConfigEnum achieveConfigType;

    private String title;

    private String description;

    private FlagTypeEnum flagType;

    private String threshold;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone= SystemConstant.TIMEZONE)
    private Date deadline;

    private NoticeTypeEnum noticeType;

    private FrequencyTypeEnum frequencyType;

    private String noticeDate;

}
