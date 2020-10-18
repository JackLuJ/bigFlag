package com.jackluan.bigflag.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/1 17:18
 */
@Data
public class BaseDto {

    private YesNoEnum isDelete;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstant.TIMEZONE)
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstant.TIMEZONE)
    private Date modifyDate;

    private Integer start;

    private Integer limit;
}
