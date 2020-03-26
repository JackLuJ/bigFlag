package com.jackluan.bigflag.common.base;

import com.jackluan.bigflag.common.enums.base.DirectoryEnum;
import lombok.Data;

import java.util.UUID;

/**
 * @Author: jack.luan
 * @Date: 2020/3/15 20:00
 */
@Data
public class FileInfo {

    private DirectoryEnum directory;

    private String uniqueCode;

    private String suffix;

    private byte[] context;

    public FileInfo(DirectoryEnum directory, byte[] context, String suffix) {
        this.directory = directory;
        this.context = context;
        this.suffix = suffix;
        this.uniqueCode = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public FileInfo(DirectoryEnum directory, String uniqueCode, String suffix) {
        this.directory = directory;
        this.suffix = suffix;
        this.uniqueCode = uniqueCode;
    }

    public String getPublicUrl() {
        return this.directory.getPath() + "/" + this.uniqueCode + this.suffix;
    }

    public byte[] getContext() {
        return this.context;
    }

}
