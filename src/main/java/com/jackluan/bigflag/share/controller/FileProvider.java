package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.IFileShareService;
import com.jackluan.bigflag.share.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.share.dto.response.OperateFileShareResponseDto;
import com.jackluan.bigflag.share.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/21 22:11
 */
@CheckToken
@RestController
public class FileProvider implements IFileShareService {

    @Autowired
    private IFileService fileService;

    @Override
    public ResultBase<OperateFileShareResponseDto> uploadFile(OperateFileShareRequestDto requestDto) {
        return fileService.uploadFile(requestDto);
    }
}
