package com.jackluan.bigflag.common.enums.file;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/16 23:30
 */
public enum StoreTypeEnum implements KeyValueEnum<Integer> {

    /**
     * OSS
     */
    OSS(1, "OSS");

    private Integer code;

    private String desc;

    StoreTypeEnum(Integer code, String desc) {
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
