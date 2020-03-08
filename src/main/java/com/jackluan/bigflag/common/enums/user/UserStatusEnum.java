package com.jackluan.bigflag.common.enums.user;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 17:21
 */
public enum  UserStatusEnum implements KeyValueEnum<Integer> {

    /**
     * effective
     */
    EFFECTIVE(1, "effective");

    private Integer code;

    private String desc;

    UserStatusEnum(Integer code, String desc) {
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
