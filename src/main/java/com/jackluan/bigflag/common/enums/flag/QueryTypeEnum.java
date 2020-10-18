package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/27 9:00
 */
public enum QueryTypeEnum implements KeyValueEnum<Integer> {

    /**
     * 自己
     */
    OWN(1, "own"),

    /**
     * 审批
     */
    APPROVE(2, "approve");


    private Integer code;

    private String desc;

    QueryTypeEnum(Integer code, String desc) {
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
