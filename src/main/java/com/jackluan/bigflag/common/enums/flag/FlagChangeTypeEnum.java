package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/1 17:15
 */
public enum FlagChangeTypeEnum implements KeyValueEnum<Integer> {

    /**
     * deadline 变更
     */
    DEADLINE(1, "deadline"),

    /**
     * threshold 变更
     */
    THRESHOLD(2, "threshold"),

    /**
     * achieve_config_type
     */
    ACHIEVE_CONFIG_TYPE(3, "achieve_config_type"),

    /**
     * approver
     */
    APPROVER(4, "approver"),

    /**
     * termination flag
     */
    TERMINATION(5, "termination flag");


    private Integer code;

    private String desc;

    FlagChangeTypeEnum(Integer code, String desc) {
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
