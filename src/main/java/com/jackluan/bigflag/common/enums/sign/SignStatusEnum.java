package com.jackluan.bigflag.common.enums.sign;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/13 19:21
 */
public enum SignStatusEnum implements KeyValueEnum<Integer> {

    /**
     * 审核中
     */
    UNDER_REVIEW(1, "under review"),

    /**
     * 已通过
     */
    PASSED(2, "passed"),

    /**
     * 不通过
     */
    NO_PASS(3, "no pass"),

    /**
     * 已过期
     */
    OVERDUE(4, "overdue");


    private Integer code;

    private String desc;

    SignStatusEnum(Integer code, String desc) {
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
