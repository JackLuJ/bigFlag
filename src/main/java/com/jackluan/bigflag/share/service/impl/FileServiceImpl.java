package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.FileInfo;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.file.StoreTypeEnum;
import com.jackluan.bigflag.common.utils.OSSUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileResponseDto;
import com.jackluan.bigflag.domain.file.handler.FileHandler;
import com.jackluan.bigflag.share.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.share.dto.response.OperateFileShareResponseDto;
import com.jackluan.bigflag.share.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * @Author: jack.luan
 * @Date: 2020/3/21 22:12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileHandler fileHandler;

    @Autowired
    private OSSUtils ossUtils;

    @Override
    public ResultBase<OperateFileShareResponseDto> uploadFile(OperateFileShareRequestDto requestDto) {

        FileInfo fileInfo = new FileInfo(requestDto.getType(), requestDto.getBytes(), requestDto.getSuffix());

        FileRequestDto fileRequestDto = new FileRequestDto();
        fileRequestDto.setStoreType(StoreTypeEnum.OSS);
        fileRequestDto.setFileName(requestDto.getFileName());
        fileRequestDto.setFileUniqueCode(fileInfo.getUniqueCode());
        fileRequestDto.setFileType(requestDto.getType());
        fileRequestDto.setSuffix(requestDto.getSuffix());
        ResultBase<FileResponseDto> resultBase = fileHandler.createFile(fileRequestDto);
        if (!resultBase.isSuccess() || resultBase.isEmptyValue()){
            return new ResultBase<OperateFileShareResponseDto>().failed(resultBase);
        }

        boolean putResult = ossUtils.putFile(SystemConstant.BUCKET_NAME, Collections.singletonList(fileInfo));
        if (!putResult){
            throw new BigFlagRuntimeException(ResultCodeConstant.OSS_PUT_FILE_FAILED);
        }

        OperateFileShareResponseDto responseDto = new OperateFileShareResponseDto();
        responseDto.setFileUniqueCode(fileInfo.getUniqueCode());
        return new ResultBase<OperateFileShareResponseDto>().success(responseDto);
    }
}
