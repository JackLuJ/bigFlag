package com.jackluan.bigflag.domain.sign.component.dataobject.extra;

import com.jackluan.bigflag.domain.sign.component.dataobject.SignDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/28 23:59
 */
@Data
@NoArgsConstructor
public class SignExtraDo extends SignDo {

    private Integer resultType;

    private Date startTime;

    private Date endTime;
}
