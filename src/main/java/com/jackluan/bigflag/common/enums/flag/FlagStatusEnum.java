package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:32
 */
public enum FlagStatusEnum implements KeyValueEnum<Integer> {

    /**
     * 未确认
     */
    UNCONFIRMED(1, "unconfirmed"),

    /**
     * 进行中
     */
    IN_PROGRESS(2, "in progress"),

    /**
     * 已完成
     */
    FINISHED(3, "finished"),

    /**
     * 已过期
     */
    OVER_DUE(4, "over due"),

    /**
     * 终止
     */
    TERMINATION(5, "termination");

    private Integer code;

    private String desc;

    FlagStatusEnum(Integer code, String desc) {
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
