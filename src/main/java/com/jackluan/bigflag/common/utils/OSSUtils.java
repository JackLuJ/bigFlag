package com.jackluan.bigflag.common.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.jackluan.bigflag.common.base.FileInfo;
import com.jackluan.bigflag.common.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 18:13
 */
@Slf4j
@Component
@ConfigurationProperties(value = OSSUtils.PREFIX)
public class OSSUtils {

    static final String PREFIX = "aliyun.oss";

    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;

    public boolean putFile(String bucketName, List<FileInfo> fileInfoList) {
        boolean success = false;
        OSS client = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            for (FileInfo fileInfo : fileInfoList) {
                byteArrayInputStream = new ByteArrayInputStream(fileInfo.getContext());
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileInfo.getPublicUrl(), byteArrayInputStream);
                client.putObject(putObjectRequest);
            }
            success = true;
        } catch (Exception e) {
            log.error("send file to OSS failed error message is {}", e.getMessage());
        } finally {
            client.shutdown();
            if (null != byteArrayInputStream) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    log.error("send file to OSS close input stream failed error message is {}", e.getMessage());
                }
            }
        }
        return success;
    }

    public URL getUrl(String bucketName, FileInfo fileInfo) {
        OSS client = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            Date expiration = new Date(System.currentTimeMillis() + SystemConstant.OSS_URL_EXPIRE_TIME);
            return client.generatePresignedUrl(bucketName, fileInfo.getPublicUrl(), expiration);
        } catch (ClientException e) {
            log.error("get file url from OSS failed error message is {}", e.getMessage());
        } finally {
            client.shutdown();
        }
        return null;
    }

    public void delFile(String bucketName, FileInfo fileInfo) {
        OSS client = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
                client.deleteObject(bucketName, fileInfo.getPublicUrl());
        } catch (Exception e) {
            log.error("del file from OSS failed error message is {}", e.getMessage());
        } finally {
            client.shutdown();
        }
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}