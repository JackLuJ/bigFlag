package com.jackluan.bigflag.domain.flag.dto.base;

import com.jackluan.bigflag.common.base.BaseDo;
import com.jackluan.bigflag.common.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 17:45
 */
@Data
@NoArgsConstructor
public class ApproveGradeBaseDto extends BaseDto {

    private Long id;

    private String score;
}
