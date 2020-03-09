package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 23:27
 */
@RequestMapping(value = "/flag")
public interface IFlagShareService {

    /**
     * 创建Flag
     * @param flagCreateShareRequestDto
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ResultBase<FlagResponseDto> createFlag(@RequestBody FlagCreateShareRequestDto flagCreateShareRequestDto);
}