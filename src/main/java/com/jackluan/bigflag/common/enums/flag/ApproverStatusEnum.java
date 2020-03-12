package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:32
 */
public enum ApproverStatusEnum implements KeyValueEnum<Integer> {

    /**
     * 未确认
     */
    UNCONFIRMED(1, "unconfirmed"),

    /**
     * 生效
     */
    EFFECTIVE(2, "effective"),

    /**
     * 失效
     */
    DISABLED(3, "disabled"),

    /**
     * 拒绝
     */
    REFUSE(4, "refuse");

    private Integer code;

    private String desc;

    ApproverStatusEnum(Integer code, String desc) {
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
