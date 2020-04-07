package com.jackluan.bigflag.share.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.flag.AchieveConfigEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/11 23:35
 */
@Data
@NoArgsConstructor
public class FlagShareResponseDto extends BaseDto implements Serializable {

    private Long id;

    private Long userId;

    private UserInfoShareResponseDto userInfo;

    private AchieveConfigEnum achieveConfigType;

    private String title;

    private String description;

    private FlagTypeEnum flagType;

    private Integer performance;

    private Integer threshold;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstant.TIMEZONE)
    private Date deadline;

    private FlagStatusEnum status;

    private List<UserInfoShareResponseDto> approverList;

    private String noticeDate;

}
