package com.jackluan.bigflag.common.utils;

import com.jackluan.bigflag.common.annotation.NotEmpty;
import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/6 23:07
 */
public class ValidationUtils {

    public static void isEmpty(Object obj) {
        boolean isEmpty = false;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(NotEmpty.class)) {
                    continue;
                }
                String name = field.getName();
                String firstLetter = name.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + name.substring(1);
                Method method = obj.getClass().getMethod(getter);
                Object val = method.invoke(obj);
                if (val == null) {
                    isEmpty = true;
                } else if (val instanceof String) {
                    isEmpty = "".equals(val);
                } else if (val instanceof Collection) {
                    isEmpty = CollectionUtils.isEmpty((Collection<?>) val);
                }
                if (isEmpty) {
                    throw new BigFlagRuntimeException(ResultCodeConstant.PARAM_VALIDATION_NOT_PASS, "param name is: " + name);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new BigFlagRuntimeException(ResultCodeConstant.VALIDATE_EXCEPTION);
        }
    }

}
