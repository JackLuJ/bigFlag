package com.jackluan.bigflag.domain.file.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.FileInfo;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.CacheObject;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.utils.OSSUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.request.FileRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.file.dto.response.FileResponseDto;
import com.jackluan.bigflag.domain.file.logic.FileGroupLogic;
import com.jackluan.bigflag.domain.file.logic.FileLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/16 23:47
 */
@Component
public class FileGroupHandler {

    private static final String cacheKey = "userId:%s,groupId:%s";

    @Autowired
    private FileLogic fileLogic;

    @Autowired
    private FileGroupLogic fileGroupLogic;

    @Autowired
    private OSSUtils ossUtils;

    public ResultBase<FileGroupResponseDto> createFileGroup(FileGroupRequestDto fileGroupRequestDto) {
        if (CollectionUtils.isEmpty(fileGroupRequestDto.getFileUniqueCodeList())) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FILE_GROUP_FILE_EMPTY_FAILED);
        }

        int existCount = fileLogic.countExtra(fileGroupRequestDto);
        if (existCount != fileGroupRequestDto.getFileUniqueCodeList().size()) {
            return new ResultBase<FileGroupResponseDto>().failed(ResultCodeConstant.CREATE_FILE_GROUP_FILE_NOT_EXIST_FAILED);
        }

        long fileGroupId = fileGroupLogic.createFileGroup(fileGroupRequestDto);
        if (fileGroupId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FILE_GROUP_FAILED);
        }

        fileGroupRequestDto.setId(fileGroupId);
        int updateCount = fileLogic.updateExtra(fileGroupRequestDto);
        if (updateCount != fileGroupRequestDto.getFileUniqueCodeList().size()) {
            throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_FILE_FAILED);
        }

        FileGroupResponseDto responseDto = new FileGroupResponseDto();
        responseDto.setId(fileGroupId);
        return new ResultBase<FileGroupResponseDto>().success(responseDto);
    }

    public ResultBase<FileGroupResponseDto> queryFileGroup(FileGroupRequestDto fileGroupRequestDto) {
        if (fileGroupRequestDto.getUserId() == null || fileGroupRequestDto.getId() == null){
            return new ResultBase<FileGroupResponseDto>().failed(ResultCodeConstant.QUERY_FILE_PARAM_VALIDATION_FAILED);
        }
        String key = String.format(cacheKey, fileGroupRequestDto.getUserId(), fileGroupRequestDto.getId());
        FileGroupResponseDto responseDto = CacheObject.fileGroupCache.get(key);
        if (responseDto != null) {
            return new ResultBase<FileGroupResponseDto>().success(responseDto);
        }

        List<FileGroupResponseDto> responseList = fileGroupLogic.queryGroup(fileGroupRequestDto);
        if (!CollectionUtils.isEmpty(responseList)) {
            responseDto = responseList.get(0);
            if (!CollectionUtils.isEmpty(responseDto.getFileList())) {
                responseDto.getFileList().forEach(file -> {
                    URL url = ossUtils.getUrl(SystemConstant.BUCKET_NAME, new FileInfo(file.getFileType(), file.getFileUniqueCode(), file.getSuffix()));
                    file.setUrl(url.toString());
                });
            }
            CacheObject.fileGroupCache.put(key, responseDto);
        }
        return new ResultBase<FileGroupResponseDto>().success(responseDto);
    }

    public ResultBase<Void> deleteFileGroup(FileGroupRequestDto fileGroupRequestDto) {

        fileGroupLogic.delete(fileGroupRequestDto);

        FileRequestDto fileRequestDto = new FileRequestDto();
        fileRequestDto.setFileGroupId(fileGroupRequestDto.getId());
        List<FileResponseDto> files = fileLogic.queryList(fileRequestDto);
        if (!CollectionUtils.isEmpty(files)) {
            files.forEach(file -> {
                ossUtils.delFile(SystemConstant.BUCKET_NAME, new FileInfo(file.getFileType(), file.getFileUniqueCode(), file.getSuffix()));
            });
        }

        fileLogic.delete(fileRequestDto);

        return new ResultBase<Void>().success();
    }
}
