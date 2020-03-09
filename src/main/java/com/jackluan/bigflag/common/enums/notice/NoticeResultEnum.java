package com.jackluan.bigflag.common.enums.notice;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 22:16
 */
public enum NoticeResultEnum implements KeyValueEnum {

    /**
     * success
     */
    SUCCESS(1, "success"),

    /**
     * failed
     */
    FAILED(2, "failed");

    private Integer code;

    private String desc;

    NoticeResultEnum(Integer code, String desc) {
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
