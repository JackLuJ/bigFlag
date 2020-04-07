package com.jackluan.bigflag.domain.notice.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 19:05
 */
@Data
@NoArgsConstructor
public class NoticeTraceDo extends BaseDo {

    private Long id;

    private Long userId;

    private Long noticeConfigId;

    private Integer noticeType;

    private Integer msgType;

    private Integer noticeResult;

    private String extra;
}
