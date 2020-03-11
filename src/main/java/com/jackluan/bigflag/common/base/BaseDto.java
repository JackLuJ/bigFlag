package com.jackluan.bigflag.common.base;

import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 17:18
 */
@Data
public class BaseDto {

    private YesNoEnum isDelete;

    private Date createDate;

    private Date modifyDate;

    private Integer start;

    private Integer limit;
}
