package com.jackluan.bigflag.common.enums.sign;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/13 19:16
 */
public enum  SignApproverResultEnum implements KeyValueEnum<Integer> {

    /**
     * 通过
     */
    APPROVE(1, "APPROVE"),

    /**
     * 不通过
     */
    REFUSE(2, "refuse");

    private Integer code;

    private String desc;

    SignApproverResultEnum(Integer code, String desc) {
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
