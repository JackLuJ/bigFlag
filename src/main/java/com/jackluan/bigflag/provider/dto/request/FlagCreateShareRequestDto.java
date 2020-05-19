package com.jackluan.bigflag.provider.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.annotation.NotEmpty;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.flag.AchieveConfigEnum;
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

    @NotEmpty
    private AchieveConfigEnum achieveConfigType;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private FlagTypeEnum flagType;

    private String threshold;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = SystemConstant.TIMEZONE)
    private Date deadline;

    @NotEmpty
    private NoticeTypeEnum noticeType;

    @NotEmpty
    private FrequencyTypeEnum frequencyType;

    @NotEmpty
    private String noticeDate;

    @NotEmpty
    private String fileUniqueCode;

}
