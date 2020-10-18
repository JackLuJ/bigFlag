package com.jackluan.bigflag.common.utils;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/22 20:26
 */
@Slf4j
public class HttpUtils {

    private static final long connectTimeOut = 10000L;

    private static final long readTimeOut = 50000L;

    private static final TimeUnit timeUtil = TimeUnit.MILLISECONDS;

    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        System.out.println(request.headers());
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.error("send http get failed. url:{}", url);
                throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_GET_REQUEST_FAILED);
            }
        } catch (IOException e) {
            log.error("send http get failed. url:{}", url);
            throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_GET_REQUEST_FAILED);
        }
    }

    public static String post(String url, String json) {
        String result = null;
        //设置媒体类型。application/json表示传递的是一个json格式的对象
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=UTF-8");
        //创建okHttpClient对象
        OkHttpClient okHttp = new OkHttpClient();
        //设置okhttp超时
        okHttp.newBuilder().connectTimeout(connectTimeOut, timeUtil).readTimeout(readTimeOut, timeUtil).build();
        //创建RequestBody对象，将参数按照指定的MediaType封装
        RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
        //创建一个Request
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.error("send http post failed. url:{}, param:{}", url, json);
                throw new IOException("unexpected code.." + response);
            }
            result = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
        } catch (IOException e) {
            log.error("send http post failed. url:{}, param:{}", url, json);
            throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_POST_REQUEST_FAILED);
        }
        return result;
    }

    public static String post(String url, byte[] bytes, String fileName) {
        String result = null;
        OkHttpClient okHttp = new OkHttpClient();
        okHttp.newBuilder().connectTimeout(connectTimeOut, timeUtil).readTimeout(readTimeOut, timeUtil).build();
        RequestBody fileBody = RequestBody.Companion.create(bytes, MediaType.Companion.parse("image/jpg"));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM).addFormDataPart("media", fileName, fileBody).build();
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.error("send file http post failed. url:{}, param:{}", url, fileName);
                throw new IOException("unexpected code.." + response);
            }
            result = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
        } catch (IOException e) {
            log.error("send http post failed. url:{}", url);
            throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_POST_REQUEST_FAILED);
        }
        return result;
    }

    public static byte[] getFile(String url){
        byte[] result = null;
        OkHttpClient okHttp = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        okHttp.newBuilder().connectTimeout(connectTimeOut, timeUtil).readTimeout(readTimeOut, timeUtil).build();
        Call call = okHttp.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                result = response.body().bytes();
            } else {
                log.error("send http get file failed. url:{}", url);
                throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_GET_FILE_REQUEST_FAILED);
            }
        } catch (IOException e) {
            log.error("send http get file failed. url:{}", url);
            throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_GET_FILE_REQUEST_FAILED);
        }
        return result;
    }
}
