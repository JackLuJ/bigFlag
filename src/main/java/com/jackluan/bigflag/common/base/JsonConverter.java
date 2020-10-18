package com.jackluan.bigflag.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/3 23:00
 */
public class JsonConverter {
    private static final Logger log = LoggerFactory.getLogger(JsonConverter.class);
    private ObjectMapper objectMapper;
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private String datePattern;

    public JsonConverter(String datePattern) {
        this.datePattern = datePattern;
        this.basicConfig();
    }

    public JsonConverter() {
        this.datePattern = DATE_PATTERN;
        this.basicConfig();
    }

    private void basicConfig(){
        this.objectMapper = this.getObjectMapper(this.datePattern);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private ObjectMapper getObjectMapper(String pattern) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat(pattern));
        return objectMapper;
    }

    public <T> T jsonToObj(String jsonStr, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(jsonStr, clazz);
        } catch (IOException var4) {
            log.error("类型转换异常");
            throw new BigFlagRuntimeException("json parse to object error");
        }
    }

    public <T> String objToJson(T t) {
        try {
            return this.objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException var3) {
            log.error("类型转换异常");
            throw new BigFlagRuntimeException("object parse to json error");
        }
    }

    public <T> T decode(String json, TypeReference<T> jsonTypeReference) {
        try {
            return this.objectMapper.readValue(json, jsonTypeReference);
        } catch (Exception var4) {
            log.error("类型转换异常");
            return null;
        }
    }
}
