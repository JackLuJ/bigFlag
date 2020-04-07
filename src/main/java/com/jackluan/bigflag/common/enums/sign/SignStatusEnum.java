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
    UNDER_REVIEW(1, "审核中"),

    /**
     * 已通过
     */
    PASSED(2, "审核通过"),

    /**
     * 不通过
     */
    NO_PASS(3, "审核未通过"),

    /**
     * 已过期
     */
    OVERDUE(4, "已过期"),

    /**
     * 终止
     */
    TERMINATION(5, "termination");


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
