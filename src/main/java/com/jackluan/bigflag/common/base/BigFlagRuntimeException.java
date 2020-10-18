package com.jackluan.bigflag.common.base;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/8 18:44
 */
public class BigFlagRuntimeException extends RuntimeException {

    private String code;

    private String msg;

    public BigFlagRuntimeException(String code) {
        super(code);
        this.code = code;
    }

    public BigFlagRuntimeException(String code, String msg) {
        super(code + " ### " + msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
