package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.domain.flag.convert.ApproverConvert;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.ApproverHandler;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.share.convert.ApproveShareConvert;
import com.jackluan.bigflag.share.convert.FlagShareConvert;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.ConfirmApproverShareRequestDto;
import com.jackluan.bigflag.share.service.IApproveService;
import com.jackluan.bigflag.share.service.IFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApproveServiceImpl implements IApproveService {

    @Autowired
    private FlagHandler flagHandler;

    @Autowired
    private ApproverHandler approverHandler;

    @Override
    public ResultBase<Void> createApprover(ApproverCreateShareRequestDto approverCreateShareRequestDto){
        approverCreateShareRequestDto.setUserId(UserUtils.getUser().getUserId());

        FlagRequestDto requestDto = new FlagRequestDto();
        requestDto.setId(approverCreateShareRequestDto.getFlagId());
        ResultBase<List<FlagResponseDto>> flagResultBase =  flagHandler.queryFlagList(requestDto);
        if (!flagResultBase.isSuccess() || CollectionUtils.isEmpty(flagResultBase.getValue())){
            return new ResultBase<Void>().failed(ResultCodeConstant.FLAG_NOT_EXIST_FAILED, "flag not exist");
        }

        ApproverRequestDto approverRequestDto = ApproveShareConvert.INSTANCE.convertToDomainDto(approverCreateShareRequestDto);
        approverRequestDto.setStatus(ApproverStatusEnum.UNCONFIRMED);
        approverHandler.createApprover(approverRequestDto);
        return new ResultBase<Void>().success();
    }

    @Override
    public ResultBase<Void> confirmApprover(ConfirmApproverShareRequestDto confirmApproverShareRequestDto) {
        ApproverRequestDto approverRequestDto = ApproveShareConvert.INSTANCE.convertToDomainDto(confirmApproverShareRequestDto);
        return approverHandler.confirmApprover(approverRequestDto);
    }
}
