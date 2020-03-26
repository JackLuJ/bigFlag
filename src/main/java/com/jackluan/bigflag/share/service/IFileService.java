package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.share.dto.response.OperateFileShareResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: jack.luan
 * @Date: 2020/3/21 22:12
 */
public interface IFileService {

    ResultBase<OperateFileShareResponseDto> uploadFile(OperateFileShareRequestDto requestDto);

}
