package com.jackluan.bigflag.common.utils;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 20:26
 */
public class HttpUtils {

    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        System.out.println(request.headers());
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_GET_REQUEST_FAILED);
            }
        } catch (IOException e) {
            throw new BigFlagRuntimeException(ResultCodeConstant.HTTP_GET_REQUEST_FAILED);
        }
    }

}
