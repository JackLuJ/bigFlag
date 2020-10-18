package com.jackluan.bigflag.domain.notice.dto.base;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.base.WeChatNoticeTypeEnum;
import com.jackluan.bigflag.common.enums.notice.NoticeResultEnum;
import com.jackluan.bigflag.common.enums.notice.NoticeTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 19:05
 */
@Data
@NoArgsConstructor
public class NoticeTraceBaseDto extends BaseDto {

    private Long id;

    private Long userId;

    private Long noticeConfigId;

    private NoticeTypeEnum noticeType;

    private WeChatNoticeTypeEnum msgType;

    private NoticeResultEnum noticeResult;

    private String extra;
}
