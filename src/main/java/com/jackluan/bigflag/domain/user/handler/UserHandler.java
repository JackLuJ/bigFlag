package com.jackluan.bigflag.domain.user.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.user.OaSubscribeStatusEnum;
import com.jackluan.bigflag.domain.user.component.dataobject.UserDo;
import com.jackluan.bigflag.domain.user.convert.UserConvert;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import com.jackluan.bigflag.domain.user.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:21
 */
@Component
public class UserHandler {

    @Autowired
    private UserLogic userLogic;

    public ResultBase<Void> createUserHandler(UserRequestDto userRequestDto) {
        UserRequestDto queryDto = new UserRequestDto();
        queryDto.setUnionId(userRequestDto.getUnionId());
        List<UserResponseDto> userList = userLogic.queryList(queryDto);
        if (CollectionUtils.isEmpty(userList)) {
            long id = userLogic.createUser(userRequestDto);
            if (id < 1) {
                throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_USER_FAILED);
            }
        } else {
            userRequestDto.setId(userList.get(0).getId());
            int count = userLogic.updateUser(userRequestDto);
            if (count < 1) {
                throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_USER_FAILED);
            }
        }
        return new ResultBase<Void>().success();
    }

    public ResultBase<Void> createOaUserHandler(UserRequestDto userRequestDto) {
        UserRequestDto queryDto = new UserRequestDto();
        if (userRequestDto.getOaSubscribeStatus() == OaSubscribeStatusEnum.SUBSCRIBE){
            queryDto.setUnionId(userRequestDto.getUnionId());
        }else {
            queryDto.setOaOpenId(userRequestDto.getOaOpenId());
        }
        List<UserResponseDto> userList = userLogic.queryList(queryDto);
        if (CollectionUtils.isEmpty(userList)) {
            long id = userLogic.createUser(userRequestDto);
            if (id < 1) {
                throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_USER_FAILED);
            }
        } else {
            userRequestDto.setId(userList.get(0).getId());
            int count = userLogic.updateUser(userRequestDto);
            if (count < 1) {
                throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_USER_FAILED);
            }
        }
        return new ResultBase<Void>().success();
    }

    public ResultBase<List<UserResponseDto>> queryUser(UserRequestDto userRequestDto) {
        List<UserResponseDto> list = userLogic.queryList(userRequestDto);
        if (CollectionUtils.isEmpty(list)) {
            return new ResultBase<List<UserResponseDto>>().failed(ResultCodeConstant.CAN_NOT_FIND_USER);
        }
        return new ResultBase<List<UserResponseDto>>().success(list);
    }

    public ResultBase<Void> refreshToken(UserRequestDto userRequestDto) {
        List<UserResponseDto> list = userLogic.queryList(userRequestDto);
        if (CollectionUtils.isEmpty(list)) {
            return new ResultBase<Void>().failed(ResultCodeConstant.TOKEN_EXPIRE);
        }
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setId(list.get(0).getId());
        requestDto.setToken(userRequestDto.getNewToken());
        int count = userLogic.updateUser(requestDto);
        if (count < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_USER_FAILED);
        }
        return new ResultBase<Void>().success();
    }

    public ResultBase<UserResponseDto> updateUserHandler(UserRequestDto userRequestDto) {
        UserRequestDto queryDto = new UserRequestDto();
        queryDto.setId(userRequestDto.getId());
        List<UserResponseDto> userList = userLogic.queryList(queryDto);
        if (CollectionUtils.isEmpty(userList)) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CAN_NOT_FIND_USER);
        }

        int count = userLogic.updateUser(userRequestDto);
        if (count < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.UPDATE_USER_FAILED);
        }

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(userList.get(0).getId());
        responseDto.setOaSubscribeStatus(userList.get(0).getOaSubscribeStatus());
        //返回旧的fileGroupId 用于删除文件
        if (null != userList.get(0).getFileGroupId()) {
            responseDto.setFileGroupId(userList.get(0).getFileGroupId());
        }

        return new ResultBase<UserResponseDto>().success(responseDto);
    }

    public ResultBase<Void> updateByUnionId(UserRequestDto userRequestDto){
        int count = userLogic.updateByUnionId(userRequestDto);
        if (count < 1){
            return new ResultBase<Void>().failed(ResultCodeConstant.UPDATE_USER_FAILED);
        }else {
            return new ResultBase<Void>().success();
        }
    }

}
