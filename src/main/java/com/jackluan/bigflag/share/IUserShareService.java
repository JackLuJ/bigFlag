package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.UserOpinionShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.UserInfoShareResponseDto;
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
    ResultBase<Void> createUser(@RequestBody UserShareRequestDto userShareRequestDto);

    /**
     * 更新用户信息
     * @param userShareRequestDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ResultBase<UserInfoShareResponseDto> updateUser(@RequestBody UserShareRequestDto userShareRequestDto);

    /**
     * 查询当前登录用户信息
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    ResultBase<UserInfoShareResponseDto> queryUser();

    /**
     * 意见接口
     * @param userOpinionShareRequestDto
     * @return
     */
    @RequestMapping(value = "/opinion", method = RequestMethod.POST)
    ResultBase<Void> opinion(@RequestBody UserOpinionShareRequestDto userOpinionShareRequestDto);

}
