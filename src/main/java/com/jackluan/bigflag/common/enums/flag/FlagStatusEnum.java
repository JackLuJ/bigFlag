package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:32
 */
public enum FlagStatusEnum implements KeyValueEnum<Integer> {

    /**
     * 未确认
     */
    UNCONFIRMED(1, "unconfirmed", FlagCategoryEnum.UNCONFIRMED),

    /**
     * 进行中
     */
    IN_PROGRESS(2, "in progress", FlagCategoryEnum.IN_PROGRESS),

    /**
     * 已完成
     */
    FINISHED(3, "finished", FlagCategoryEnum.IN_PROGRESS),

    /**
     * 已过期
     */
    OVER_DUE(4, "over due", FlagCategoryEnum.FINISHED),

    /**
     * 用户认为达成
     */
    ACHIEVE(5, "achieve", FlagCategoryEnum.FINISHED),

    /**
     * 用户认为未达成
     */
    NOT_ACHIEVE(6, "not achieve", FlagCategoryEnum.FINISHED);

    private Integer code;

    private String desc;

    private FlagCategoryEnum category;

    FlagStatusEnum(Integer code, String desc, FlagCategoryEnum category) {
        this.code = code;
        this.desc = desc;
        this.category = category;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public FlagCategoryEnum getCategory() {
        return category;
    }

    public static List<FlagStatusEnum> getStatusListByCategory(FlagCategoryEnum category){
        List<FlagStatusEnum> resultList = new ArrayList<>();
        for (FlagStatusEnum statusEnum : FlagStatusEnum.values()){
            if (category == statusEnum.getCategory()){
                resultList.add(statusEnum);
            }
        }
        return resultList;
    }
}
