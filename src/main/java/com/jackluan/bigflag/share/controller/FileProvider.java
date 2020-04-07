package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.IFileShareService;
import com.jackluan.bigflag.share.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.share.dto.response.OperateFileShareResponseDto;
import com.jackluan.bigflag.share.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
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
        log.info("into uploadFile method. param is {}",converter.objToJson(requestDto));
        return fileService.uploadFile(requestDto);
    }
}
