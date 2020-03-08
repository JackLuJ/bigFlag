package com.jackluan.bigflag.domain.flag.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:41
 */
@Data
@NoArgsConstructor
public class FlagDo extends BaseDo {

    private Long id;

    private Long userId;

    private Long achieveConfigId;

    private Long noticeConfigId;

    private String title;

    private String description;

    private Integer flagType;

    private String threshold;

    private Date deadline;

    private Integer status;

    private Integer sequenceNo;

}
