package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.QueryFileListRequestDto;
import com.jackluan.bigflag.provider.dto.response.OperateFileShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.QueryFileListResponseDto;
import com.jackluan.bigflag.provider.dto.response.wechat.UploadAppFileResponseDto;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/21 22:12
 */
public interface IFileService {

    ResultBase<OperateFileShareResponseDto> uploadFile(OperateFileShareRequestDto requestDto);

    ResultBase<QueryFileListResponseDto> queryFileList(QueryFileListRequestDto requestDto);

    UploadAppFileResponseDto uploadWeChatAppFile(byte[] bytes, String fileName);

    void uploadAppPicture();

}
