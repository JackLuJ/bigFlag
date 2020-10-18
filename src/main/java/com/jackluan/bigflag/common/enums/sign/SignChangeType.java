package com.jackluan.bigflag.common.enums.sign;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/1 17:15
 */
public enum SignChangeType implements KeyValueEnum<Integer> {

    /**
     * approver
     */
    APPROVER(1, "approver"),

    /**
     * termination
     */
    TERMINATION(2, "termination");


    private Integer code;

    private String desc;

    SignChangeType(Integer code, String desc) {
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
