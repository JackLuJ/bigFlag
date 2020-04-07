package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;
import com.jackluan.bigflag.common.utils.DateUtils;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:32
 */
public enum FlagTypeEnum implements KeyValueEnum<Integer> {

    /**
     * 按完成天数计算
     */
    DAILY_FLAG(1, "daily flag"),

    /**
     * 按完成次数计算
     */
    TIMES_FLAG(2, "times flag");

    private Integer code;

    private String desc;

    FlagTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
