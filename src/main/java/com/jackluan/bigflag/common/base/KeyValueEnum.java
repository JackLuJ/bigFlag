package com.jackluan.bigflag.common.base;

import java.io.Serializable;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/1 17:01
 */
public interface KeyValueEnum<T> extends Serializable {

    T getCode();

    String getDesc();

}
