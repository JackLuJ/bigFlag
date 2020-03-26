package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.file.handler.FileGroupHandler;
import com.jackluan.bigflag.domain.file.handler.FileHandler;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import com.jackluan.bigflag.domain.notice.handler.NoticeConfigHandler;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.share.convert.FileShareConvert;
import com.jackluan.bigflag.share.convert.FlagShareConvert;
import com.jackluan.bigflag.share.convert.NoticeShareConvert;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagShareRequestDto;
import com.jackluan.bigflag.share.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.share.dto.response.FlagShareResponseDto;
import com.jackluan.bigflag.share.service.IFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 22:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlagServiceImpl implements IFlagService {

    @Autowired
    private NoticeConfigHandler noticeConfigHandler;

    @Autowired
    private FlagHandler flagHandler;

    @Autowired
    private FileGroupHandler fileGroupHandler;

    @Autowired
    private UserHandler userHandler;

    @Override
    public ResultBase<FlagShareResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) {

        flagCreateShareRequestDto.setUserId(UserUtils.getUser().getUserId());
        //1.调用notice领域 生成notice配置 并返回Id
        NoticeConfigRequestDto noticeConfigRequestDto = NoticeShareConvert.INSTANCE.convertToNoticeConfig(flagCreateShareRequestDto);
        ResultBase<NoticeConfigResponseDto> noticeResult = noticeConfigHandler.createNoticeConfig(noticeConfigRequestDto);
        if (!noticeResult.isSuccess()) {
            return new ResultBase<FlagShareResponseDto>().failed(noticeResult);
        }

        //2.调用Flag领域生成flag
        FlagRequestDto flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(flagCreateShareRequestDto);
        flagRequestDto.setNoticeConfigId(noticeResult.getValue().getId());
        flagRequestDto.setStatus(FlagStatusEnum.IN_PROGRESS);
        ResultBase<FlagResponseDto> resultBase = flagHandler.createFlag(flagRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<FlagShareResponseDto>().failed(resultBase);
        }

        FlagShareResponseDto flagShareResponseDto = FlagShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());
        return new ResultBase<FlagShareResponseDto>().success(flagShareResponseDto);
    }

    @Override
    public ResultBase<Page<FlagShareResponseDto>> queryFlag(Page<FlagShareRequestDto> flagShareRequestDto) {
        flagShareRequestDto.getCondition().setUserId(UserUtils.getUser().getUserId());
        Page<FlagRequestDto> flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(flagShareRequestDto);
        flagRequestDto.getCondition().setShowApprover(true);
        ResultBase<Page<FlagResponseDto>> resultBase = flagHandler.queryFlagListPage(flagRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<Page<FlagShareResponseDto>>().failed(resultBase);
        }
        Page<FlagShareResponseDto> dataList = FlagShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());

        if (!CollectionUtils.isEmpty(dataList.getResults())) {
            dataList.getResults().forEach(flagShareResponseDto -> {
                if (CollectionUtils.isEmpty(flagShareResponseDto.getApproverList())) {
                    return;
                }
                flagShareResponseDto.getApproverList().forEach(approver -> {
                    if (null == approver.getUserId()){
                        return;
                    }
                    UserRequestDto userRequestDto = new UserRequestDto();
                    userRequestDto.setId(approver.getUserId());
                    ResultBase<List<UserResponseDto>> userResultBase = userHandler.queryUser(userRequestDto);
                    if (!userResultBase.isSuccess() || CollectionUtils.isEmpty(userResultBase.getValue()) || null == userResultBase.getValue().get(0).getFileGroupId()){
                        return;
                    }

                    FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
                    fileGroupRequestDto.setUserId(approver.getUserId());
                    fileGroupRequestDto.setId(userResultBase.getValue().get(0).getFileGroupId());
                    ResultBase<FileGroupResponseDto> fileResultBase = fileGroupHandler.queryFileGroup(fileGroupRequestDto);
                    if (fileResultBase.isSuccess() && fileResultBase.getValue() != null && !CollectionUtils.isEmpty(fileResultBase.getValue().getFileList())){
                        approver.setUrl(fileResultBase.getValue().getFileList().get(0).getUrl());
                    }
                });
            });
        }
        return new ResultBase<Page<FlagShareResponseDto>>().success(dataList);
    }
}
