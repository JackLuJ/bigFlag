package com.jackluan.bigflag.common.base;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:30
 */
@Mapper
public interface KeyValueEnumMapper {

    default Integer getIntegerCode(KeyValueEnum<Integer> keyValueEnum) {
        return keyValueEnum == null ? null : keyValueEnum.getCode();
    }

    default String getStringCode(KeyValueEnum<String> keyValueEnum) {
        return keyValueEnum == null ? null : keyValueEnum.getCode();
    }

    default <T extends Enum<T> & KeyValueEnum> T getKeyValueExtEnum(@TargetType Class<T> tClass, Object obj) {
        if (null == obj) {
            return null;
        }
        Object code;
        if (obj instanceof KeyValueEnum) {
            code = ((KeyValueEnum) obj).getCode();
        } else {
            code = obj;
        }
        Enum[] enums = tClass.getEnumConstants();
        for (int i = 0; i < enums.length; ++i) {
            T result = (T) enums[i];
            if (result.getCode().equals(code)) {
                return result;
            }
        }
        return null;
    }
}
