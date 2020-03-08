package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:17
 */
public interface IUserService {

    ResultBase<Void> createUser(UserShareRequestDto userShareRequestDto);

}
