package com.jackluan.bigflag.domain.sign.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 22:04
 */
@Data
@NoArgsConstructor
public class SignTraceDo extends BaseDo {

    private Long id;

    private Long signId;

    private Long userId;

    private Long flagId;

    private String description;

    private Long fileGroupId;

    private Integer threshold;

    private Date achieveDate;

    private Date deadline;

    private Integer status;

    private Integer sequenceNo;

    private String approverInfo;

}
