package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.annotation.PassToken;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.LoginShareRequestDto;
import com.jackluan.bigflag.share.dto.response.LoginShareResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 15:57
 */
@RequestMapping(value = "/base")
public interface ILoginShareService {

    /**
     * login
     * @param loginShareRequestDto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResultBase<LoginShareResponseDto> login(@RequestBody LoginShareRequestDto loginShareRequestDto);

    /**
     * refreshToken
     * @param loginShareRequestDto
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    ResultBase<LoginShareResponseDto> refreshToken(@RequestBody LoginShareRequestDto loginShareRequestDto);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    ResultBase<String> test();
}
