package com.jackluan.bigflag.domain.sign.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 22:05
 */
@Data
@NoArgsConstructor
public class SignApproverDo extends BaseDo {

    private Long id;

    private Long signId;

    private Long approverId;

    private Long approverUserId;

    private String score;

    private Integer resultType;

    private String description;

}
