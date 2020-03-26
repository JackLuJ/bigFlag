package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.convert.UserShareConvert;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.share.dto.response.LoginShareResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 20:01
 */
@RequestMapping(value = "/user")
public interface IUserShareService {

    /**
     * 创建用户
     * @param userShareRequestDto
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ResultBase<Void> createUser(@RequestBody UserShareRequestDto userShareRequestDto);

    /**
     * 更新用户信息
     * @param userShareRequestDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ResultBase<Void> updateUser(@RequestBody UserShareRequestDto userShareRequestDto);

}
