package com.jackluan.bigflag.common.enums.notice;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/9 22:16
 */
public enum NoticeTypeEnum implements KeyValueEnum {

    /**
     * we-chat notice
     */
    WE_CHAT(1, "we-chat notice");

    private Integer code;

    private String desc;

    NoticeTypeEnum(Integer code, String desc) {
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
