package com.jackluan.bigflag.provider.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.utils.CommonUtils;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.file.handler.FileGroupHandler;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserOpinionResponseDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.domain.user.handler.UserOpinionHandler;
import com.jackluan.bigflag.provider.convert.UserShareConvert;
import com.jackluan.bigflag.provider.dto.request.UserOpinionShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.UserInfoShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.wechat.SecretInfoResponseDto;
import com.jackluan.bigflag.provider.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private UserOpinionHandler userOpinionHandler;

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
        if (!StringUtils.isEmpty(userShareRequestDto.getFileUniqueCode())) {
            FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
            fileGroupRequestDto.setUserId(userShareRequestDto.getId());
            fileGroupRequestDto.setFileUniqueCodeList(Collections.singletonList(userShareRequestDto.getFileUniqueCode()));
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

        //解密微信数据 获取unionId
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setId(userShareRequestDto.getId());
        ResultBase<List<UserResponseDto>> userResult = userHandler.queryUser(userRequest);
        if (!userResult.isSuccess() || CollectionUtils.isEmpty(userResult.getValue())) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CAN_NOT_FIND_USER);
        }
        UserResponseDto oldUser = userResult.getValue().get(0);
        if (!StringUtils.isEmpty(userShareRequestDto.getEncryptedData()) && !StringUtils.isEmpty(userShareRequestDto.getIv())) {
            log.info("query user secret info before:{},{},{}", userShareRequestDto.getEncryptedData(), oldUser.getSessionKey(), userShareRequestDto.getIv());
            String secretInfo = CommonUtils.decryptOfDiyIV(userShareRequestDto.getEncryptedData(), oldUser.getSessionKey(), userShareRequestDto.getIv());
            log.info("query user secret info response:{}", secretInfo);
            JsonConverter converter = new JsonConverter();
            SecretInfoResponseDto secretInfoResponse = converter.jsonToObj(secretInfo, SecretInfoResponseDto.class);
            userRequestDto.setUnionId(secretInfoResponse.getUnionId());
        }

        ResultBase<UserResponseDto> resultBase = userHandler.updateUserHandler(userRequestDto);

        //3.删除历史头像信息
        if (!StringUtils.isEmpty(userShareRequestDto.getFileUniqueCode()) && resultBase.isSuccess() && !resultBase.isEmptyValue() && null != resultBase.getValue().getFileGroupId()) {
            FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
            fileGroupRequestDto.setId(resultBase.getValue().getFileGroupId());
            fileGroupHandler.deleteFileGroup(fileGroupRequestDto);
        }

        UserInfoShareResponseDto responseDto = new UserInfoShareResponseDto();
        responseDto.setUserId(resultBase.getValue().getId());
        responseDto.setOaSubscribeStatus(resultBase.getValue().getOaSubscribeStatus());
        return new ResultBase<UserInfoShareResponseDto>().success(responseDto);
    }

    @Override
    public ResultBase<UserInfoShareResponseDto> queryUser(UserShareRequestDto userShareRequestDto) {
        UserInfoShareResponseDto responseDto = new UserInfoShareResponseDto();
        responseDto.setUserId(userShareRequestDto.getId());

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(userShareRequestDto.getId());
        ResultBase<List<UserResponseDto>> userResultBase = userHandler.queryUser(userRequestDto);
        if (!userResultBase.isSuccess() || CollectionUtils.isEmpty(userResultBase.getValue())) {
            return new ResultBase<UserInfoShareResponseDto>().failed(ResultCodeConstant.CAN_NOT_FIND_USER);
        }
        responseDto.setNickname(userResultBase.getValue().get(0).getNickname());
        responseDto.setOaSubscribeStatus(userResultBase.getValue().get(0).getOaSubscribeStatus());

        if (null != userResultBase.getValue().get(0).getFileGroupId()) {
            FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
            fileGroupRequestDto.setUserId(userShareRequestDto.getId());
            fileGroupRequestDto.setId(userResultBase.getValue().get(0).getFileGroupId());
            ResultBase<FileGroupResponseDto> fileResultBase = fileGroupHandler.queryFileGroup(fileGroupRequestDto);
            if (fileResultBase.isSuccess() && fileResultBase.getValue() != null && !CollectionUtils.isEmpty(fileResultBase.getValue().getFileList())) {
                responseDto.setUrl(fileResultBase.getValue().getFileList().get(0).getUrl());
            }
        }

        return new ResultBase<UserInfoShareResponseDto>().success(responseDto);
    }

    @Override
    public ResultBase<Void> opinion(UserOpinionShareRequestDto userOpinionShareRequestDto) {
        userOpinionShareRequestDto.setUserId(UserUtils.getUser().getUserId());
        ResultBase<UserOpinionResponseDto> resultBase = userOpinionHandler.create(UserShareConvert.INSTANCE.convertToDomainDto(userOpinionShareRequestDto));
        if (!resultBase.isSuccess()) {
            return new ResultBase<Void>().failed(resultBase);
        }
        return new ResultBase<Void>().success();
    }
}
