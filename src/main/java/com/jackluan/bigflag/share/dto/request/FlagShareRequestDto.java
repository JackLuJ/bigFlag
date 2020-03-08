package com.jackluan.bigflag.share.dto.request;

import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:28
 */
@Data
@NoArgsConstructor
public class FlagShareRequestDto {

    private Long id;

    private Long userId;

    private Long achieveConfigId;

    private Long noticeConfigId;

    private String title;

    private String description;

    private FlagTypeEnum flagType;

    private String threshold;

    private Date deadline;

    private FlagStatusEnum status;

}
