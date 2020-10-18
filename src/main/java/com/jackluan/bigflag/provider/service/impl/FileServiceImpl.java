package com.jackluan.bigflag.provider.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.FileInfo;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.CacheObject;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.constant.WeChatConstant;
import com.jackluan.bigflag.common.enums.base.DirectoryEnum;
import com.jackluan.bigflag.common.enums.file.StoreTypeEnum;
import com.jackluan.bigflag.common.utils.HttpUtils;
import com.jackluan.bigflag.common.utils.OSSUtils;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.common.utils.WeChatUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.request.FileRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.file.dto.response.FileResponseDto;
import com.jackluan.bigflag.domain.file.handler.FileGroupHandler;
import com.jackluan.bigflag.domain.file.handler.FileHandler;
import com.jackluan.bigflag.provider.dto.request.OperateFileShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.QueryFileListRequestDto;
import com.jackluan.bigflag.provider.dto.response.OperateFileShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.QueryFileListResponseDto;
import com.jackluan.bigflag.provider.dto.response.wechat.SecCheckResponseDto;
import com.jackluan.bigflag.provider.dto.response.wechat.UploadAppFileResponseDto;
import com.jackluan.bigflag.provider.service.IFileService;
import com.jackluan.bigflag.provider.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/21 22:12
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileHandler fileHandler;

    @Autowired
    private FileGroupHandler fileGroupHandler;

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private WeChatUtils weChatUtils;

    @Value("${we-chat.app.upload-image-url}")
    private String appUploadFileUrl;


    @Override
    public ResultBase<OperateFileShareResponseDto> uploadFile(OperateFileShareRequestDto requestDto) {
        if (StringUtils.isEmpty(requestDto.getFileName())) {
            requestDto.setFileName(UUID.randomUUID().toString());
        }

        FileInfo fileInfo = new FileInfo(requestDto.getType(), requestDto.getBytes(), requestDto.getSuffix());

        //针对凭证做微信图片认证
        if (requestDto.getType() == DirectoryEnum.SIGN) {
            SecCheckResponseDto secCheckResponseDto = weChatUtils.imgSecCheck(fileInfo.getContext(), requestDto.getFileName());
            if (secCheckResponseDto.getErrcode() != 0) {
                return new ResultBase<OperateFileShareResponseDto>().failed(ResultCodeConstant.IMG_CHECK_NOT_PASS);
            }
        }

        FileRequestDto fileRequestDto = new FileRequestDto();
        fileRequestDto.setStoreType(StoreTypeEnum.OSS);
        fileRequestDto.setFileName(requestDto.getFileName());
        fileRequestDto.setFileUniqueCode(fileInfo.getUniqueCode());
        fileRequestDto.setFileType(requestDto.getType());
        fileRequestDto.setSuffix(requestDto.getSuffix());
        ResultBase<FileResponseDto> resultBase = fileHandler.createFile(fileRequestDto);
        if (!resultBase.isSuccess() || resultBase.isEmptyValue()) {
            return new ResultBase<OperateFileShareResponseDto>().failed(resultBase);
        }

        boolean putResult = ossUtils.putFile(SystemConstant.BUCKET_NAME, Collections.singletonList(fileInfo));
        if (!putResult) {
            throw new BigFlagRuntimeException(ResultCodeConstant.OSS_PUT_FILE_FAILED);
        }

        OperateFileShareResponseDto responseDto = new OperateFileShareResponseDto();
        responseDto.setFileUniqueCode(fileInfo.getUniqueCode());
        return new ResultBase<OperateFileShareResponseDto>().success(responseDto);
    }

    @Override
    public ResultBase<QueryFileListResponseDto> queryFileList(QueryFileListRequestDto requestDto) {
        QueryFileListResponseDto responseDto = new QueryFileListResponseDto();

        FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
        fileGroupRequestDto.setUserId(UserUtils.getUser().getUserId());
        fileGroupRequestDto.setId(requestDto.getFileGroupId());
        ResultBase<FileGroupResponseDto> fileResultBase = fileGroupHandler.queryFileGroup(fileGroupRequestDto);
        if (fileResultBase.isSuccess() && fileResultBase.getValue() != null && !CollectionUtils.isEmpty(fileResultBase.getValue().getFileList())) {
            responseDto.setUrls(fileResultBase.getValue().getFileList().stream().map(FileResponseDto::getUrl).collect(Collectors.toList()));
        }
        return new ResultBase<QueryFileListResponseDto>().success(responseDto);
    }

    @Override
    public UploadAppFileResponseDto uploadWeChatAppFile(byte[] bytes, String fileName) {
        String uploadImgUrl = String.format(appUploadFileUrl, weChatUtils.getAccessToken(WeChatConstant.APP));
        String response = HttpUtils.post(uploadImgUrl, bytes, fileName);
        log.info("upload weChat app file response:{}", response);
        JsonConverter converter = new JsonConverter();
        return converter.jsonToObj(response, UploadAppFileResponseDto.class);
    }

    @Override
    public void uploadAppPicture() {
        Resource resource = new ClassPathResource("/picture.jpg");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            UploadAppFileResponseDto responseDto = this.uploadWeChatAppFile(FileCopyUtils.copyToByteArray(inputStream), resource.getFilename());
            if (null != responseDto && !StringUtils.isEmpty(responseDto.getMedia_id())) {
                CacheObject.weChatCache.put(SystemConstant.WE_CHAT_APP_FILE_ID_KEY, responseDto.getMedia_id());
            }
        } catch (Exception e) {
            log.error("upload weChat app picture failed, can not find file in resource");
            return;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("upload weChat app close inputStream failed");
                }
            }
        }
    }
}
