package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.ApproveSignShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.SignShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.SignShareResponseDto;
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

    /**
     * 查询Sign
     * @param signShareRequestDtoPage
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    ResultBase<Page<SignShareResponseDto>> querySign(@RequestBody Page<SignShareRequestDto> signShareRequestDtoPage);

    /**
     * 审批Sign
     * @param approveSignShareRequestDto
     * @return
     */
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    ResultBase<Void> approveSign(@RequestBody ApproveSignShareRequestDto approveSignShareRequestDto);

    /**
     * 查询Sign
     * @param signShareRequestDto
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    ResultBase<SignShareResponseDto> detail(@RequestBody SignShareRequestDto signShareRequestDto);

    /**
     * 删除Sign
     * @param signShareRequestDto
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResultBase<Void> delete(@RequestBody SignShareRequestDto signShareRequestDto);

}
