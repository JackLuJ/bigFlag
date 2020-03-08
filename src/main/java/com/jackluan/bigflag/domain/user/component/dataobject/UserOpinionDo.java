package com.jackluan.bigflag.domain.user.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 16:59
 */
@Data
@NoArgsConstructor
public class UserOpinionDo extends BaseDo {

    private Long id;

    private Long userId;

    private String description;

    private String mobile;

    private String xwName;

    private String qqNo;

}
