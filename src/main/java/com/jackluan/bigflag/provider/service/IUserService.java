package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.UserOpinionShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.UserInfoShareResponseDto;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/3 23:17
 */
public interface IUserService {

    ResultBase<Void> createUser(UserShareRequestDto userShareRequestDto);

    ResultBase<UserInfoShareResponseDto> updateUser(UserShareRequestDto userShareRequestDto);

    ResultBase<UserInfoShareResponseDto> queryUser(UserShareRequestDto userShareRequestDto);

    ResultBase<Void> opinion(UserOpinionShareRequestDto userOpinionShareRequestDto);

}
