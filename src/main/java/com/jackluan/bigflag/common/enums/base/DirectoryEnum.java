package com.jackluan.bigflag.common.enums.base;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/16 23:56
 */
public enum DirectoryEnum implements KeyValueEnum<Integer> {

    /**
     * 背景图
     */
    BACKGROUND(1, "background", "background"),

    /**
     * 头像
     */
    HEAD(2, "head", "head"),

    /**
     * 目标
     */
    FLAG(3, "flag", "flag"),

    /**
     * 打卡
     */
    SIGN(4, "sign", "sign");

    private Integer code;

    private String desc;

    private String path;

    DirectoryEnum(Integer code, String desc, String path) {
        this.code = code;
        this.desc = desc;
        this.path = path;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getPath() {
        return path;
    }
}
