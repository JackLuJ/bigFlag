package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.UserOpinionShareRequestDto;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.share.dto.response.UserInfoShareResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:17
 */
public interface IUserService {

    ResultBase<Void> createUser(UserShareRequestDto userShareRequestDto);

    ResultBase<UserInfoShareResponseDto> updateUser(UserShareRequestDto userShareRequestDto);

    ResultBase<UserInfoShareResponseDto> queryUser(UserShareRequestDto userShareRequestDto);

    ResultBase<Void> opinion(UserOpinionShareRequestDto userOpinionShareRequestDto);

}
