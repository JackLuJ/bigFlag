package com.jackluan.bigflag.common.base;

import com.jackluan.bigflag.common.constant.ResultCodeConstant;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:00
 */
public class ResultBase<T> {

    private boolean isSuccess;

    private String code;

    private String msg;

    private T value;

    private String trackId;

    public ResultBase<T> success() {
        this.code = ResultCodeConstant.SUCCESS;
        this.isSuccess = true;
        return this;
    }

    public ResultBase<T> success(T t) {
        this.code = ResultCodeConstant.SUCCESS;
        this.isSuccess = true;
        this.value = t;
        return this;
    }

    public ResultBase<T> failed(String code) {
        this.isSuccess = false;
        this.code = code;
        return this;
    }

    public ResultBase<T> failed(String code, String msg) {
        this.isSuccess = false;
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResultBase<T> failed(String code, String msg, T t) {
        this.isSuccess = false;
        this.code = code;
        this.msg = msg;
        this.value = t;
        return this;
    }
}
