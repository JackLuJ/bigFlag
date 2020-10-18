package com.jackluan.bigflag.domain.notice.dto.base;

import com.jackluan.bigflag.common.base.BaseDo;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.notice.FrequencyTypeEnum;
import com.jackluan.bigflag.common.enums.notice.NoticeTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 19:03
 */
@Data
@NoArgsConstructor
public class NoticeConfigBaseDto extends BaseDto {

    private Long id;

    private NoticeTypeEnum noticeType;

    private FrequencyTypeEnum frequencyType;

    private String noticeDate;
}
