package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/8 19:15
 */
public enum AchieveConfigEnum implements KeyValueEnum<Integer> {

    /**
     * 困难
     */
    DIFFICULT(1, "difficult"),

    /**
     * 中等
     */
    NORMAL(2, "normal"),

    /**
     * 简单
     */
    EASY(3, "easy");

    private Integer code;

    private String desc;

    AchieveConfigEnum(Integer code, String desc) {
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
