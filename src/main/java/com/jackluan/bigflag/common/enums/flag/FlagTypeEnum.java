package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;
import com.jackluan.bigflag.common.utils.DateUtils;

import java.util.Date;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 23:32
 */
public enum FlagTypeEnum implements KeyValueEnum<Integer> {

    /**
     * 按完成天数计算
     */
    DAILY_FLAG(1, "daily flag", false, true),

    /**
     * 按完成次数计算
     */
    TIMES_FLAG(2, "times flag", false, false),

    /**
     * 按完成天数计算(强制计算成功或失败)
     */
    DAILY_FLAG_FORCE(3, "daily flag force", true, true),

    /**
     * 按完成次数计算(强制计算成功或失败)
     */
    TIMES_FLAG_FORCE(4, "times flag force", true, false);

    private Integer code;

    private String desc;

    private Boolean force;

    private Boolean daily;

    FlagTypeEnum(Integer code, String desc, Boolean force, Boolean daily) {
        this.code = code;
        this.desc = desc;
        this.force = force;
        this.daily = daily;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public Boolean getForce() {
        return force;
    }

    public Boolean getDaily() {
        return daily;
    }
}
