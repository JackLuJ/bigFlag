package com.jackluan.bigflag.common.constant;

import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 11:48
 */
public class CacheObject {

    public static ConcurrentHashMap<String, FileGroupResponseDto> fileGroupCache = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> weChatCache = new ConcurrentHashMap<>(4);
}
