package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.file.handler.FileGroupHandler;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.share.convert.FileShareConvert;
import com.jackluan.bigflag.share.convert.UserShareConvert;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.share.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.share.dto.response.UserInfoShareResponseDto;
import com.jackluan.bigflag.share.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private FileGroupHandler fileGroupHandler;

    @Override
    public ResultBase<Void> createUser(UserShareRequestDto userShareRequestDto) {
        UserRequestDto requestDto = UserShareConvert.INSTANCE.convertToDomainDto(userShareRequestDto);
        return userHandler.createUserHandler(requestDto);
    }

    @Override
    public ResultBase<UserInfoShareResponseDto> updateUser(UserShareRequestDto userShareRequestDto) {

        userShareRequestDto.setId(UserUtils.getUser().getUserId());

        //1.调用File领域 校验File是否存在 并生成FileGroup返回Id
        Long fileGroupId = null;
        if (!CollectionUtils.isEmpty(userShareRequestDto.getFileUniqueCodes())) {
            FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
            fileGroupRequestDto.setUserId(userShareRequestDto.getId());
            fileGroupRequestDto.setFileUniqueCodeList(userShareRequestDto.getFileUniqueCodes());
            ResultBase<FileGroupResponseDto> fileGroupResponse = fileGroupHandler.createFileGroup(fileGroupRequestDto);
            if (!fileGroupResponse.isSuccess() || fileGroupResponse.isEmptyValue()) {
                return new ResultBase<UserInfoShareResponseDto>().failed(fileGroupResponse);
            }
            fileGroupId = fileGroupResponse.getValue().getId();
        }

        //2.更新用户信息
        UserRequestDto userRequestDto = UserShareConvert.INSTANCE.convertToDomainDto(userShareRequestDto);
        if (fileGroupId != null) {
            userRequestDto.setFileGroupId(fileGroupId);
        }
        ResultBase<UserResponseDto> resultBase = userHandler.updateUserHandler(userRequestDto);

        //3.删除历史头像信息
        if (!CollectionUtils.isEmpty(userShareRequestDto.getFileUniqueCodes()) && resultBase.isSuccess() && !resultBase.isEmptyValue() && null != resultBase.getValue().getFileGroupId()) {
            FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
            fileGroupRequestDto.setId(resultBase.getValue().getFileGroupId());
            fileGroupHandler.deleteFileGroup(fileGroupRequestDto);
        }

        UserInfoShareResponseDto responseDto = new UserInfoShareResponseDto();
        responseDto.setUserId(resultBase.getValue().getId());
        return new ResultBase<UserInfoShareResponseDto>().success(responseDto);
    }

    @Override
    public ResultBase<UserInfoShareResponseDto> queryUser(UserShareRequestDto userShareRequestDto) {
        UserInfoShareResponseDto responseDto = new UserInfoShareResponseDto();
        responseDto.setUserId(userShareRequestDto.getId());

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(userShareRequestDto.getId());
        ResultBase<List<UserResponseDto>> userResultBase = userHandler.queryUser(userRequestDto);
        if (!userResultBase.isSuccess() || CollectionUtils.isEmpty(userResultBase.getValue()) || null == userResultBase.getValue().get(0).getFileGroupId()) {
            return new ResultBase<UserInfoShareResponseDto>().failed(ResultCodeConstant.CAN_NOT_FIND_USER);
        }
        responseDto.setNickname(userResultBase.getValue().get(0).getNickname());

        FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
        fileGroupRequestDto.setUserId(userShareRequestDto.getId());
        fileGroupRequestDto.setId(userResultBase.getValue().get(0).getFileGroupId());
        ResultBase<FileGroupResponseDto> fileResultBase = fileGroupHandler.queryFileGroup(fileGroupRequestDto);
        if (fileResultBase.isSuccess() && fileResultBase.getValue() != null && !CollectionUtils.isEmpty(fileResultBase.getValue().getFileList())) {
            responseDto.setUrl(fileResultBase.getValue().getFileList().get(0).getUrl());
        }

        return new ResultBase<UserInfoShareResponseDto>().success(responseDto);
    }
}
