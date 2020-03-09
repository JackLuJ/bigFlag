package com.jackluan.bigflag.domain.notice.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 19:03
 */
@Data
@NoArgsConstructor
public class NoticeConfigDo extends BaseDo {

    private Long id;

    private Integer noticeType;

    private Integer frequencyType;

    private String noticeDate;
}
