package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/5/24 22:54
 */
public enum FlagStatusCategoryEnum implements KeyValueEnum<Integer> {
    /**
     * 未确认
     */
    UNCONFIRMED(1, "UNCONFIRMED"),

    /**
     * 进行中
     */
    IN_PROGRESS(2, "IN_PROGRESS"),

    /**
     * 已结束
     */
    FINISHED(3, "FINISHED");


    private Integer code;

    private String desc;

    FlagStatusCategoryEnum(Integer code, String desc) {
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
