package com.jackluan.bigflag.domain.flag.dto.base;

import com.jackluan.bigflag.common.base.BaseDo;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.flag.AchieveConfigEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:41
 */
@Data
@NoArgsConstructor
public class FlagBaseDto extends BaseDto {

    private Long id;

    private Long userId;

    private Long noticeConfigId;

    private AchieveConfigEnum achieveConfigType;

    private String title;

    private String description;

    private FlagTypeEnum flagType;

    private String threshold;

    private Date deadline;

    private FlagStatusEnum status;

    private Integer sequenceNo;

}
