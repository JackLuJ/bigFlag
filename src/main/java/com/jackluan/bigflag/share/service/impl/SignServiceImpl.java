package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.enums.flag.FlagUpdateTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.file.handler.FileGroupHandler;
import com.jackluan.bigflag.domain.flag.dto.request.CreateSingInfoRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.CreateSingInfoResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.UpdateFlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.domain.sign.convert.SignConvert;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import com.jackluan.bigflag.domain.sign.handler.SignHandler;
import com.jackluan.bigflag.share.convert.FileShareConvert;
import com.jackluan.bigflag.share.convert.FlagShareConvert;
import com.jackluan.bigflag.share.convert.SignShareConvert;
import com.jackluan.bigflag.share.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.share.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.share.service.ISignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SignServiceImpl implements ISignService {

    @Autowired
    private SignHandler signHandler;

    @Autowired
    private FileGroupHandler fileGroupHandler;

    @Autowired
    private FlagHandler flagHandler;

    @Override
    public ResultBase<CreateSignShareResponseDto> createSign(CreateSignShareRequestDto createSignShareRequestDto) {

        //1.调用File领域 校验File是否存在 并生成FileGroup返回Id
        Long fileGroupId = null;
        if (!CollectionUtils.isEmpty(createSignShareRequestDto.getFileUniqueCodes())){
            FileGroupRequestDto fileGroupRequestDto = FileShareConvert.INSTANCE.convert(createSignShareRequestDto);
            ResultBase<FileGroupResponseDto> fileGroupResponse = fileGroupHandler.createFileGroup(fileGroupRequestDto);
            if (!fileGroupResponse.isSuccess() || fileGroupResponse.isEmptyValue()) {
                return new ResultBase<CreateSignShareResponseDto>().failed(fileGroupResponse);
            }
            fileGroupId = fileGroupResponse.getValue().getId();
        }


        //2.调用Flag领域 获取审批人信息 以及审批通过阈值
        CreateSingInfoRequestDto createSingInfoRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(createSignShareRequestDto);
        ResultBase<CreateSingInfoResponseDto> flagResponse = flagHandler.getSignInfo(createSingInfoRequestDto);
        if (!flagResponse.isSuccess() || flagResponse.isEmptyValue()) {
            return new ResultBase<CreateSignShareResponseDto>().failed(flagResponse);
        }

        //3.调用Sign领域 生成打卡信息
        SignRequestDto signRequestDto = SignShareConvert.INSTANCE.convertToDomainDto(createSignShareRequestDto);
        signRequestDto.setFileGroupId(fileGroupId);
        signRequestDto.setApproverList(SignShareConvert.INSTANCE.convertToDomainDto(flagResponse.getValue().getApproverList()));
        ResultBase<SignResponseDto> signResultBase = signHandler.createSign(signRequestDto);
        if (!signResultBase.isSuccess() || signResultBase.isEmptyValue()){
            return new ResultBase<CreateSignShareResponseDto>().failed(flagResponse);
        }

        //4.打卡状态为通过更新flag信息
        if (SignStatusEnum.PASSED == signResultBase.getValue().getStatus()){
            FlagRequestDto flagRequestDto = new FlagRequestDto();
            flagRequestDto.setId(createSignShareRequestDto.getFlagId());
            flagRequestDto.setUserId(createSignShareRequestDto.getUserId());
            flagRequestDto.setFlagUpdateType(FlagUpdateTypeEnum.SIGN_PASS);
            flagHandler.updateFlag(flagRequestDto);
        }

        CreateSignShareResponseDto responseDto = new CreateSignShareResponseDto();
        responseDto.setSignId(signResultBase.getValue().getId());
        return new ResultBase<CreateSignShareResponseDto>().success(responseDto);
    }

}
