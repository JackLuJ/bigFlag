package com.jackluan.bigflag.common.enums.flag;

import com.jackluan.bigflag.common.base.KeyValueEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 23:32
 */
public enum FlagStatusEnum implements KeyValueEnum<Integer> {

    /**
     * 未确认
     */
    UNCONFIRMED(1, "unconfirmed", FlagStatusCategoryEnum.UNCONFIRMED),

    /**
     * 进行中
     */
    IN_PROGRESS(2, "in progress", FlagStatusCategoryEnum.IN_PROGRESS),

    /**
     * 已完成
     */
    FINISHED(3, "finished", FlagStatusCategoryEnum.IN_PROGRESS),

    /**
     * 已过期
     */
    OVER_DUE(4, "over due", FlagStatusCategoryEnum.FINISHED),

    /**
     * 用户认为达成
     */
    ACHIEVE(5, "achieve", FlagStatusCategoryEnum.FINISHED),

    /**
     * 用户认为未达成
     */
    NOT_ACHIEVE(6, "not achieve", FlagStatusCategoryEnum.FINISHED);

    private Integer code;

    private String desc;

    private FlagStatusCategoryEnum category;

    FlagStatusEnum(Integer code, String desc, FlagStatusCategoryEnum category) {
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

    public FlagStatusCategoryEnum getCategory() {
        return category;
    }

    public static List<FlagStatusEnum> getStatusListByCategory(FlagStatusCategoryEnum category){
        List<FlagStatusEnum> resultList = new ArrayList<>();
        for (FlagStatusEnum statusEnum : FlagStatusEnum.values()){
            if (category == statusEnum.getCategory()){
                resultList.add(statusEnum);
            }
        }
        return resultList;
    }
}
