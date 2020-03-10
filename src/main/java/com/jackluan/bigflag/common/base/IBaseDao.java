package com.jackluan.bigflag.common.base;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/1 17:45
 */
public interface IBaseDao<T> {

    int insert(T t);

    int delete(T t);

    int update(T t);

    List<T> list(T t);

    int count(T t);

}
