package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.FlagShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.FlagUpdateRequestDto;
import com.jackluan.bigflag.provider.dto.response.FlagShareResponseDto;
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
    ResultBase<FlagShareResponseDto> createFlag(@RequestBody FlagCreateShareRequestDto flagCreateShareRequestDto);

    /**
     * 查询Flag
     * @param flagShareRequestDto
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    ResultBase<Page<FlagShareResponseDto>> queryFlag(@RequestBody Page<FlagShareRequestDto> flagShareRequestDto);

    /**
     * 查询详细信息
     * @param flagShareRequestDto
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    ResultBase<FlagShareResponseDto> queryDetail(@RequestBody FlagShareRequestDto flagShareRequestDto);

    /**
     * 更新flag
     * @param flagUpdateRequestDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ResultBase<FlagShareResponseDto> update(@RequestBody FlagUpdateRequestDto flagUpdateRequestDto);

}
