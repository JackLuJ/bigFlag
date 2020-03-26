package com.jackluan.bigflag.common.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.jackluan.bigflag.common.base.FileInfo;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.base.DirectoryEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 18:13
 */
@Slf4j
@Component
@ConfigurationProperties(value = OSSUtils.prefix)
public class OSSUtils {

    static final String prefix = "aliyun.oss";

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

    public URL getUrl(String bucketName, String key) {
        OSS client = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            Date expiration = new Date(System.currentTimeMillis() + SystemConstant.OSS_URL_EXPIRE_TIME);
            return client.generatePresignedUrl(bucketName, key, expiration);
        } catch (ClientException e) {
            log.error("get file url from OSS failed error message is {}", e.getMessage());
        } finally {
            client.shutdown();
        }
        return null;
    }

    public void delFile(String bucketName, List<String> filePathList) {
        OSS client = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            filePathList.forEach(filePath -> {
                client.deleteObject(bucketName, filePath);
            });
        } catch (Exception e) {
            log.error("del file from OSS failed error message is {}", e.getMessage());
        } finally {
            client.shutdown();
        }
    }

    public static void main(String[] args) throws IOException {
//        byte[] b = Files.readAllBytes(Paths.get("D://image1.jpg"));
//        String base64 = Base64.getEncoder().encodeToString(b);
//        byte[] bytes = Base64.getDecoder().decode(base64);
//        List<FileInfo> array = Arrays.asList(new FileInfo(DirectoryEnum.FLAG, bytes, ".jpg"));
//        OSSUtils ossUtils = new OSSUtils();
//        System.out.println(getUrl(SystemConstant.BUCKET_NAME, "flag/458f4fbdc2554e8cac4a85545b016a6b.jpg").getPath());
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