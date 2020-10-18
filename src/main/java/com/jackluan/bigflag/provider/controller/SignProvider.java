package com.jackluan.bigflag.provider.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import com.jackluan.bigflag.common.utils.ValidationUtils;
import com.jackluan.bigflag.provider.dto.request.DeleteSignRequestDto;
import com.jackluan.bigflag.share.ISignShareService;
import com.jackluan.bigflag.provider.dto.request.ApproveSignShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.SignShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.SignShareResponseDto;
import com.jackluan.bigflag.provider.service.ISignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:45
 */
@Slf4j
@CheckToken
@RestController
public class SignProvider implements ISignShareService {
    private JsonConverter converter = new JsonConverter();

    @Autowired
    private ISignService signService;

    @Override
    public ResultBase<CreateSignShareResponseDto> createSign(CreateSignShareRequestDto createSignShareRequestDto) {
        return signService.createSign(createSignShareRequestDto);
    }

    @Override
    public ResultBase<Page<SignShareResponseDto>> querySign(Page<SignShareRequestDto> signShareRequestDtoPage) {
        return signService.querySign(signShareRequestDtoPage);
    }

    @Override
    public ResultBase<Void> approveSign(ApproveSignShareRequestDto approveSignShareRequestDto) {
        ValidationUtils.isEmpty(approveSignShareRequestDto);
        if (approveSignShareRequestDto.getResultType() != SignApproverResultEnum.APPROVE && approveSignShareRequestDto.getResultType() != SignApproverResultEnum.REFUSE){
            return new ResultBase<Void>().failed(ResultCodeConstant.PARAM_VALIDATION_NOT_PASS, "please set current resultType");
        }
        return signService.approveSign(approveSignShareRequestDto);
    }

    @Override
    public ResultBase<SignShareResponseDto> detail(SignShareRequestDto signShareRequestDto) {
        Page<SignShareRequestDto> signShareRequestDtoPage = new Page<>();
        signShareRequestDtoPage.setCondition(signShareRequestDto);
        ResultBase<Page<SignShareResponseDto>> resultBase = signService.querySign(signShareRequestDtoPage);
        if (!resultBase.isSuccess()){
            return new ResultBase<SignShareResponseDto>().failed(resultBase);
        }
        if (resultBase.isEmptyValue() || CollectionUtils.isEmpty(resultBase.getValue().getResults())){
            return new ResultBase<SignShareResponseDto>().success();
        }

        return new ResultBase<SignShareResponseDto>().success(resultBase.getValue().getResults().get(0));
    }

    @Override
    public ResultBase<Void> delete(DeleteSignRequestDto deleteSignRequestDto) {
        return signService.delete(deleteSignRequestDto);
    }
}
