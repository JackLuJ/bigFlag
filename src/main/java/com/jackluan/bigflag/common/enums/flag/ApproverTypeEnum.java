package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:21
 */
public enum ApproverTypeEnum implements KeyValueEnum<Integer> {

    /**
     * 普通审核人
     */
    NORMAL(1, "normal", 1);

    private Integer code;

    private String desc;

    private Integer score;

    ApproverTypeEnum(Integer code, String desc, Integer score) {
        this.code = code;
        this.desc = desc;
        this.score = score;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public Integer getScore() {
        return score;
    }
}
