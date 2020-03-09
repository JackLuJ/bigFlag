package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/8 16:29
 */
public enum ThresholdCalculationTypeEnum implements KeyValueEnum<Integer> {

    /**
     * every one approve
     */
    FULL(1, "every one approve"),

    /**
     * half of person approve
     */
    HALF(2, "half of person approve"),

    /**
     * one person approve
     */
    ONE(3, "one person approve");

    private Integer code;

    private String desc;

    ThresholdCalculationTypeEnum(Integer code, String desc) {
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
