package com.jackluan.bigflag.common.enums.user;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/5 22:17
 */
public enum OaSubscribeStatusEnum implements KeyValueEnum<Integer> {

    /**
     * effective
     */
    SUBSCRIBE(1, "subscribe"),

    /**
     *
     */
    UN_SUBSCRIBE(2, "un_subscribe");

    private Integer code;

    private String desc;

    OaSubscribeStatusEnum(Integer code, String desc) {
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
