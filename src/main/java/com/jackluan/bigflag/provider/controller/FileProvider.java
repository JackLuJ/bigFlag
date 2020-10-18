package com.jackluan.bigflag.provider.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.IFileShareService;
import com.jackluan.bigflag.provider.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.QueryFileListRequestDto;
import com.jackluan.bigflag.provider.dto.response.OperateFileShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.QueryFileListResponseDto;
import com.jackluan.bigflag.provider.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/21 22:11
 */
@Slf4j
@CheckToken
@RestController
public class FileProvider implements IFileShareService {
    private JsonConverter converter = new JsonConverter();

    @Autowired
    private IFileService fileService;

    @Override
    public ResultBase<OperateFileShareResponseDto> uploadFile(OperateFileShareRequestDto requestDto) {
        return fileService.uploadFile(requestDto);
    }

    @Override
    public ResultBase<QueryFileListResponseDto> queryFileList(QueryFileListRequestDto requestDto) {
        return fileService.queryFileList(requestDto);
    }
}
