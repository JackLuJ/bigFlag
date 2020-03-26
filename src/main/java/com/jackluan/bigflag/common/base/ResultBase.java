package com.jackluan.bigflag.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;

import java.io.Serializable;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:00
 */
public class ResultBase<T> implements Serializable {

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

    public ResultBase<T> failed(BigFlagRuntimeException e) {
        this.isSuccess = false;
        this.code = e.getCode();
        this.msg = e.getMsg();
        return this;
    }

    public ResultBase<T> failed(ResultBase resultBase) {
        this.isSuccess = false;
        this.code = resultBase.getCode();
        this.msg = resultBase.getMsg();
        return this;
    }

    public ResultBase<T> failed(String code) {
        this.isSuccess = false;
        this.code = code;
        return this;
    }

    public ResultBase<T> failed(String code, T t) {
        this.isSuccess = false;
        this.code = code;
        this.value = t;
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

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public boolean isEmptyValue() {
        return null == this.value;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        JsonConverter jsonConverter = new JsonConverter();
        return jsonConverter.objToJson(this);
    }
}
