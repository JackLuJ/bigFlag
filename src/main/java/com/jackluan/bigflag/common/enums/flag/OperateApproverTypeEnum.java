package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/4/3 4:52
 */
public enum OperateApproverTypeEnum implements KeyValueEnum<Integer> {
    /**
     * EXIT
     */
    EXIT(1, "exit"),

    /**
     * REMOVE
     */
    REMOVE(2, "remove");

    private Integer code;

    private String desc;

    OperateApproverTypeEnum(Integer code, String desc) {
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
