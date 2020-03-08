package com.jackluan.bigflag.common.enums.base;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 17:04
 */
public enum YesNoEnum implements KeyValueEnum<String> {
    /**
     * yes
     */
    YES("Y", "Yes"),

    /**
     * no
     */
    NO("N", "No");

    private String code;

    private String desc;

    YesNoEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
