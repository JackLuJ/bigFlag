package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagUpdateTypeEnum;
import com.jackluan.bigflag.common.enums.flag.OperateApproverTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignChangeType;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.domain.flag.convert.ApproverConvert;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.UpdateFlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.ApproverHandler;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.handler.NoticeConfigHandler;
import com.jackluan.bigflag.domain.sign.dto.base.SignApproverBaseDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignApproverResponseDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import com.jackluan.bigflag.domain.sign.handler.SignApproverHandler;
import com.jackluan.bigflag.domain.sign.handler.SignHandler;
import com.jackluan.bigflag.share.convert.ApproveShareConvert;
import com.jackluan.bigflag.share.convert.FlagShareConvert;
import com.jackluan.bigflag.share.dto.request.*;
import com.jackluan.bigflag.share.dto.request.notice.NoticeBingSuccessRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeInviteSuccessRequestDto;
import com.jackluan.bigflag.share.dto.response.ApproverShareResponseDto;
import com.jackluan.bigflag.share.dto.response.UserInfoShareResponseDto;
import com.jackluan.bigflag.share.service.IApproveService;
import com.jackluan.bigflag.share.service.IFlagService;
import com.jackluan.bigflag.share.service.INoticeService;
import com.jackluan.bigflag.share.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    @Autowired
    private SignHandler signHandler;

    @Autowired
    private NoticeConfigHandler noticeConfigHandler;

    @Autowired
    private IUserService userService;

    @Autowired
    private INoticeService noticeService;

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
        ResultBase<ApproverResponseDto> resultBase = approverHandler.createApprover(approverRequestDto);
        if (resultBase.isSuccess()){
            NoticeInviteSuccessRequestDto noticeInviteSuccess = new NoticeInviteSuccessRequestDto();
            noticeInviteSuccess.setInviteUserId(approverCreateShareRequestDto.getUserId());
            noticeInviteSuccess.setUserId(flagResultBase.getValue().get(0).getUserId());
            noticeService.noticeInviteSuccess(noticeInviteSuccess);

            return new ResultBase<Void>().success();
        }else {
            return new ResultBase<Void>().failed(resultBase);
        }
    }

    @Override
    public ResultBase<Void> confirmApprover(ConfirmApproverShareRequestDto confirmApproverShareRequestDto) {
        confirmApproverShareRequestDto.setFlagUserId(UserUtils.getUser().getUserId());
        ApproverRequestDto approverRequestDto = ApproveShareConvert.INSTANCE.convertToDomainDto(confirmApproverShareRequestDto);
        ResultBase<ApproverResponseDto> resultBase = approverHandler.confirmApprover(approverRequestDto);
        if (!resultBase.isSuccess()){
            return new ResultBase<Void>().failed(resultBase);
        }
        NoticeBingSuccessRequestDto noticeBingSuccess = new NoticeBingSuccessRequestDto();
        noticeBingSuccess.setFlagId(resultBase.getValue().getFlagId());
        noticeBingSuccess.setUserId(confirmApproverShareRequestDto.getFlagUserId());
        noticeService.noticeBindSuccess(noticeBingSuccess);

        return new ResultBase<Void>().success();
    }

    @Override
    public ResultBase<Page<ApproverShareResponseDto>> queryList(Page<QueryApproverShareRequestDto> queryApproverShareRequestDto) {
        List<ApproverStatusEnum> statusList = new ArrayList<>();
        statusList.add(ApproverStatusEnum.UNCONFIRMED);
        statusList.add(ApproverStatusEnum.EFFECTIVE);
        queryApproverShareRequestDto.getCondition().setStatusList(statusList);
        Page<ApproverRequestDto> requestDtoPage = ApproveShareConvert.INSTANCE.convertToDomainDto(queryApproverShareRequestDto);
        ResultBase<Page<ApproverResponseDto>> resultBase = approverHandler.queryPage(requestDtoPage);
        if (!resultBase.isSuccess()){
            return new ResultBase<Page<ApproverShareResponseDto>>().failed(resultBase);
        }

        Page<ApproverShareResponseDto> responseDtoPage = ApproveShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());
        if (!CollectionUtils.isEmpty(responseDtoPage.getResults())){
            responseDtoPage.getResults().forEach(approver -> {
                if (null == approver.getUserId()) {
                    return;
                }
                UserShareRequestDto userRequest = new UserShareRequestDto();
                userRequest.setId(approver.getUserId());
                ResultBase<UserInfoShareResponseDto> response = userService.queryUser(userRequest);
                if (!response.isSuccess() || response.isEmptyValue()) {
                    return;
                }

                approver.setNickname(response.getValue().getNickname());
                approver.setUrl(response.getValue().getUrl());
            });
        }

        return new ResultBase<Page<ApproverShareResponseDto>>().success(responseDtoPage);
    }

    @Override
    public ResultBase<Void> operate(ApproverOperateShareRequestDto approverOperateShareRequestDto) {
        Long userId = UserUtils.getUser().getUserId();

        //组装参数
        ApproverRequestDto approverRequest = new ApproverRequestDto();
        approverRequest.setUserId(approverOperateShareRequestDto.getUserId());
        approverRequest.setFlagId(approverOperateShareRequestDto.getFlagId());
        approverRequest.setAddApprover(YesNoEnum.NO);
        if (approverOperateShareRequestDto.getOperate() == OperateApproverTypeEnum.EXIT){
            approverRequest.setUserId(userId);
        }

        List<ApproverRequestDto> approverList = new ArrayList<>();
        approverList.add(approverRequest);

        FlagRequestDto flagRequestDto = new FlagRequestDto();
        flagRequestDto.setId(approverOperateShareRequestDto.getFlagId());
        flagRequestDto.setFlagUpdateType(FlagUpdateTypeEnum.FLAG_UPDATE);
        flagRequestDto.setApproverList(approverList);
        if (approverOperateShareRequestDto.getOperate() == OperateApproverTypeEnum.REMOVE){
            flagRequestDto.setUserId(userId);
        }

        //校验apprver
        ResultBase<List<ApproverResponseDto>> approverResultBase = approverHandler.queryList(approverRequest);
        if (!approverResultBase.isSuccess() || CollectionUtils.isEmpty(approverResultBase.getValue())){
            return new ResultBase<Void>().failed(ResultCodeConstant.APPROVER_NOT_EXITS);
        }
        approverRequest.setId(approverResultBase.getValue().get(0).getId());

        //更新flag
        ResultBase<UpdateFlagResponseDto> resultBase = flagHandler.updateFlag(flagRequestDto);
        if (!resultBase.isSuccess()){
            return new ResultBase<Void>().failed(resultBase);
        }

        //更新未完成的sign
        SignApproverRequestDto signApproverBaseDto = new SignApproverRequestDto();
        signApproverBaseDto.setApproverId(approverResultBase.getValue().get(0).getId());
        SignRequestDto signRequestDto = new SignRequestDto();
        signRequestDto.setChangeType(SignChangeType.APPROVER);
        signRequestDto.setDelApprover(signApproverBaseDto);
        ResultBase<SignResponseDto> signResultBase = signHandler.updateSign(signRequestDto);
        if (!signResultBase.isSuccess()){
            throw new BigFlagRuntimeException(ResultCodeConstant.CHANGE_SIGN_FAILED);
        }

        //如果sign通过 更新flag performance
        if (!signResultBase.isEmptyValue() && signResultBase.getValue().getPassCount() > 0) {
            FlagRequestDto updatePerformance = new FlagRequestDto();
            updatePerformance.setId(approverOperateShareRequestDto.getFlagId());
            updatePerformance.setFlagUpdateType(FlagUpdateTypeEnum.SIGN_PASS);
            updatePerformance.setPassCount(signResultBase.getValue().getPassCount());
            ResultBase<UpdateFlagResponseDto> flagResult = flagHandler.updateFlag(updatePerformance);
            if (flagResult.isSuccess() && !flagResult.isEmptyValue() && flagResult.getValue().getFlagFinish()){
                NoticeConfigRequestDto noticeConfig = new NoticeConfigRequestDto();
                noticeConfig.setId(flagResult.getValue().getNoticeConfigId());
                noticeConfigHandler.delete(noticeConfig);
            }
        }
        return new ResultBase<Void>().success();
    }

    @Override
    public ResultBase<Boolean> needApprove(QueryApproverShareRequestDto queryApproverShareRequestDto) {
        FlagRequestDto requestDto = new FlagRequestDto();
        requestDto.setUserId(UserUtils.getUser().getUserId());
        requestDto.setId(queryApproverShareRequestDto.getFlagId());
        ResultBase<List<FlagResponseDto>> flagResultBase =  flagHandler.queryFlagList(requestDto);
        if (!flagResultBase.isSuccess() || CollectionUtils.isEmpty(flagResultBase.getValue())){
            return new ResultBase<Boolean>().failed(ResultCodeConstant.FLAG_NOT_EXIST_FAILED, "flag not exist");
        }

        ApproverRequestDto approverRequestDto = new ApproverRequestDto();
        approverRequestDto.setFlagId(queryApproverShareRequestDto.getFlagId());
        ResultBase<Boolean> resultBase = approverHandler.queryUnderApproveCount(approverRequestDto);
        return resultBase;
    }
}
