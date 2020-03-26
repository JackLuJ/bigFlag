package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.share.dto.response.CreateSignShareResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:27
 */
@RequestMapping(value = "/sign")
public interface ISignShareService {

    /**
     * 打卡
     * @param createSignShareRequestDto
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ResultBase<CreateSignShareResponseDto> createSign(@RequestBody CreateSignShareRequestDto createSignShareRequestDto);

}
