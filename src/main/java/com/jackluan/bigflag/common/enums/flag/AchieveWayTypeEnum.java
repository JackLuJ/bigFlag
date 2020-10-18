package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/8 16:23
 */
public enum AchieveWayTypeEnum implements KeyValueEnum<Integer> {

    /**
     * greater than
     */
    GREATER_THAN(1, "greater than"),

    /**
     * equal to
     */
    EQUAL_TO(2, "equal to"),

    /**
     * less than
     */
    LESS_THAN(3, "less than");

    private Integer code;

    private String desc;

    AchieveWayTypeEnum(Integer code, String desc) {
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
