package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/20 22:28
 */
public enum FlagUpdateTypeEnum implements KeyValueEnum<Integer> {

    /**
     * sign change
     */
    SIGN_CHANGE(1, "sign change"),

    /**
     * flag update
     */
    FLAG_UPDATE(2, "flag update");

    private Integer code;

    private String desc;

    FlagUpdateTypeEnum(Integer code, String desc) {
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
