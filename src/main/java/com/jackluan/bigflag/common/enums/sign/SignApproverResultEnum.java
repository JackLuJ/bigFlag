package com.jackluan.bigflag.common.enums.sign;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/13 19:16
 */
public enum  SignApproverResultEnum implements KeyValueEnum<Integer> {

    /**
     * 没权限
     */
    NO_PERMISSION(-1, "no permission"),

    /**
     * 未确认
     */
    UN_CONFIRM(0, "未确认"),

    /**
     * 通过
     */
    APPROVE(1, "审核通过"),

    /**
     * 不通过
     */
    REFUSE(2, "审核不通过");

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
