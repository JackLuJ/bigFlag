package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.QueryFileListRequestDto;
import com.jackluan.bigflag.provider.dto.response.OperateFileShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.QueryFileListResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/21 21:00
 */
@RequestMapping(value = "/file")
public interface IFileShareService {

    /**
     * 上传文件
     * @param requestDto
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    ResultBase<OperateFileShareResponseDto> uploadFile(@RequestBody OperateFileShareRequestDto requestDto);

    /**
     * 查询文件列表
     * @param requestDto
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    ResultBase<QueryFileListResponseDto> queryFileList(@RequestBody QueryFileListRequestDto requestDto);

}
