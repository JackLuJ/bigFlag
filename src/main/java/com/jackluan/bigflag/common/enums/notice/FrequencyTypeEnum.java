package com.jackluan.bigflag.common.enums.notice;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/9 22:16
 */
public enum FrequencyTypeEnum implements KeyValueEnum {

    /**
     * daily
     */
    DAILY(1, "daily");

    private Integer code;

    private String desc;

    FrequencyTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Object getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
