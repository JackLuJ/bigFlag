package com.jackluan.bigflag.domain.file.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.domain.file.dto.request.FileRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileResponseDto;
import com.jackluan.bigflag.domain.file.logic.FileLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/16 23:47
 */
@Component
public class FileHandler {

    @Autowired
    private FileLogic fileLogic;

    public ResultBase<FileResponseDto> createFile(FileRequestDto fileRequestDto){
        long fileId = fileLogic.createFile(fileRequestDto);
        if (fileId < 1){
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FILE_FAILED);
        }
        FileResponseDto fileResponseDto = new FileResponseDto();
        fileResponseDto.setId(fileId);
        return new ResultBase<FileResponseDto>().success(fileResponseDto);
    }
}
