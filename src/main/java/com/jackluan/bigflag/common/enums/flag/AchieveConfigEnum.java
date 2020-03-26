package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: jack.luan
 * @Date: 2020/3/8 19:15
 */
public enum AchieveConfigEnum implements KeyValueEnum<Integer> {

    /**
     * 困难
     */
    DIFFICULT(1, "difficult", new BigDecimal(1), 0, RoundingMode.HALF_UP),

    /**
     * 中等
     */
    NORMAL(2, "normal", new BigDecimal(2), 0, RoundingMode.HALF_UP),

    /**
     * 简单
     */
    EASY(3, "easy", new BigDecimal(-1), 0, RoundingMode.HALF_UP);

    private Integer code;

    private String desc;

    private BigDecimal factor;

    private int scale;

    private RoundingMode roundingMode;

    AchieveConfigEnum(Integer code, String desc, BigDecimal factor, int scale, RoundingMode roundingMode) {
        this.code = code;
        this.desc = desc;
        this.factor = factor;
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public int getScale() {
        return scale;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }
}
