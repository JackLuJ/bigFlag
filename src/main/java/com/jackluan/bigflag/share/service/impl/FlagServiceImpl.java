package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.FlagChangeTypeEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagUpdateTypeEnum;
import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignChangeType;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.domain.file.handler.FileGroupHandler;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.UpdateFlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import com.jackluan.bigflag.domain.notice.handler.NoticeConfigHandler;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import com.jackluan.bigflag.domain.sign.handler.SignHandler;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.share.convert.FlagShareConvert;
import com.jackluan.bigflag.share.convert.NoticeShareConvert;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagShareRequestDto;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.share.dto.response.FlagShareResponseDto;
import com.jackluan.bigflag.share.dto.response.UserInfoShareResponseDto;
import com.jackluan.bigflag.share.service.IFlagService;
import com.jackluan.bigflag.share.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: jack.luan
 * @Date: 2020/3/9 22:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlagServiceImpl implements IFlagService {

    @Autowired
    private IUserService userService;

    @Autowired
    private NoticeConfigHandler noticeConfigHandler;

    @Autowired
    private FlagHandler flagHandler;

    @Autowired
    private FileGroupHandler fileGroupHandler;

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private SignHandler signHandler;

    @Override
    public ResultBase<FlagShareResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) {

        flagCreateShareRequestDto.setUserId(UserUtils.getUser().getUserId());
        //1.调用notice领域 生成notice配置 并返回Id
        NoticeConfigRequestDto noticeConfigRequestDto = NoticeShareConvert.INSTANCE.convertToNoticeConfig(flagCreateShareRequestDto);
        ResultBase<NoticeConfigResponseDto> noticeResult = noticeConfigHandler.createNoticeConfig(noticeConfigRequestDto);
        if (!noticeResult.isSuccess()) {
            return new ResultBase<FlagShareResponseDto>().failed(noticeResult);
        }

        //2.调用Flag领域生成flag
        FlagRequestDto flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(flagCreateShareRequestDto);
        flagRequestDto.setNoticeConfigId(noticeResult.getValue().getId());
        flagRequestDto.setStatus(FlagStatusEnum.IN_PROGRESS);
        ResultBase<FlagResponseDto> resultBase = flagHandler.createFlag(flagRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<FlagShareResponseDto>().failed(resultBase);
        }

        FlagShareResponseDto flagShareResponseDto = FlagShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());
        return new ResultBase<FlagShareResponseDto>().success(flagShareResponseDto);
    }

    @Override
    public ResultBase<Page<FlagShareResponseDto>> queryFlag(Page<FlagShareRequestDto> flagShareRequestDto) {
        flagShareRequestDto.getCondition().setUserId(UserUtils.getUser().getUserId());

        //查询flagList
        Page<FlagRequestDto> flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(flagShareRequestDto);
        flagRequestDto.getCondition().setShowApprover(true);
        ResultBase<Page<FlagResponseDto>> resultBase = flagHandler.queryFlagListPage(flagRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<Page<FlagShareResponseDto>>().failed(resultBase);
        }
        Page<FlagShareResponseDto> dataList = FlagShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());

        //填充审批人信息
        if (!CollectionUtils.isEmpty(dataList.getResults())) {
            dataList.getResults().forEach(flagShareResponseDto -> {

                UserShareRequestDto flagUserRequest = new UserShareRequestDto();
                flagUserRequest.setId(flagShareResponseDto.getUserId());
                ResultBase<UserInfoShareResponseDto> flagUserResponse = userService.queryUser(flagUserRequest);
                if (flagUserResponse.isSuccess() && !flagUserResponse.isEmptyValue()) {
                    flagShareResponseDto.setUserInfo(flagUserResponse.getValue());
                }

                if (CollectionUtils.isEmpty(flagShareResponseDto.getApproverList())) {
                    return;
                }
                flagShareResponseDto.getApproverList().forEach(approver -> {
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
            });
        }
        return new ResultBase<Page<FlagShareResponseDto>>().success(dataList);
    }

    @Override
    public ResultBase<FlagShareResponseDto> queryDetail(FlagShareRequestDto flagShareRequestDto) {

        //设置参数查询flag
        flagShareRequestDto.setUserId(null);
        flagShareRequestDto.setQueryType(QueryTypeEnum.OWN);

        Page<FlagShareRequestDto> page = new Page<>();
        page.setCondition(flagShareRequestDto);

        Page<FlagRequestDto> flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(page);
        flagRequestDto.getCondition().setShowApprover(true);

        ResultBase<Page<FlagResponseDto>> resultBase = flagHandler.queryFlagListPage(flagRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<FlagShareResponseDto>().failed(resultBase);
        }
        if (CollectionUtils.isEmpty(resultBase.getValue().getResults())) {
            return new ResultBase<FlagShareResponseDto>().success();
        }

        Page<FlagShareResponseDto> dataList = FlagShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());
        FlagShareResponseDto responseDto = dataList.getResults().get(0);

        //查询通知信息
        NoticeConfigRequestDto noticeConfigRequest = new NoticeConfigRequestDto();
        noticeConfigRequest.setId(resultBase.getValue().getResults().get(0).getNoticeConfigId());
        ResultBase<List<NoticeConfigResponseDto>> noticeResultBase = noticeConfigHandler.queryNoticeConfig(noticeConfigRequest);
        if (noticeResultBase.isSuccess() && !noticeResultBase.isEmptyValue() && !CollectionUtils.isEmpty(noticeResultBase.getValue())) {
            responseDto.setNoticeDate(noticeResultBase.getValue().get(0).getNoticeDate());
        }

        //查询创建人用户信息
        UserShareRequestDto flagUserRequest = new UserShareRequestDto();
        flagUserRequest.setId(responseDto.getUserId());
        ResultBase<UserInfoShareResponseDto> flagUserResponse = userService.queryUser(flagUserRequest);
        if (flagUserResponse.isSuccess() && !flagUserResponse.isEmptyValue()) {
            responseDto.setUserInfo(flagUserResponse.getValue());
        }

        //查询审批人信息
        if (!CollectionUtils.isEmpty(responseDto.getApproverList())) {
            responseDto.getApproverList().forEach(approver -> {
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

        return new ResultBase<FlagShareResponseDto>().success(responseDto);
    }

    @Override
    public ResultBase<FlagShareResponseDto> update(FlagShareRequestDto flagShareRequestDto) {
        flagShareRequestDto.setUserId(UserUtils.getUser().getUserId());

        //1.校验flag是否存在
        FlagRequestDto flagRequest = FlagShareConvert.INSTANCE.convertToDomainDto(flagShareRequestDto);
        ResultBase<List<FlagResponseDto>> flagResultBase = flagHandler.queryFlagList(flagRequest);
        if (!flagResultBase.isSuccess() || flagResultBase.isEmptyValue()) {
            return new ResultBase<FlagShareResponseDto>().failed(ResultCodeConstant.FLAG_NOT_EXIST_FAILED);
        }

        //更新notice
        if (!StringUtils.isEmpty(flagShareRequestDto.getNoticeDate())) {
            NoticeConfigRequestDto noticeRequest = new NoticeConfigRequestDto();
            noticeRequest.setId(flagResultBase.getValue().get(0).getNoticeConfigId());
            noticeRequest.setNoticeDate(flagShareRequestDto.getNoticeDate());
            ResultBase<Void> noticeResult = noticeConfigHandler.updateNotice(noticeRequest);
            if (!noticeResult.isSuccess()) {
                return new ResultBase<FlagShareResponseDto>().failed(noticeResult);
            }
        }

        if (flagShareRequestDto.getDeadline() != null || flagShareRequestDto.getAchieveConfigType() != null) {
            //2.更新flag
            flagRequest.setFlagUpdateType(FlagUpdateTypeEnum.FLAG_UPDATE);
            ResultBase<UpdateFlagResponseDto> updateResultBase = flagHandler.updateFlag(flagRequest);

            //3.重算未完成的sign
            if (updateResultBase.isSuccess() && !CollectionUtils.isEmpty(updateResultBase.getValue().getChangeTypes()) && updateResultBase.getValue().getChangeTypes().contains(FlagChangeTypeEnum.TERMINATION)) {
                List<FlagChangeTypeEnum> updateTypes = updateResultBase.getValue().getChangeTypes();
                SignRequestDto signRequestDto = new SignRequestDto();
                signRequestDto.setFlagId(flagShareRequestDto.getFlagId());
                signRequestDto.setChangeType(SignChangeType.TERMINATION);
                ResultBase<SignResponseDto> signResult = signHandler.updateSign(signRequestDto);
                if (!signResult.isSuccess()) {
                    throw new BigFlagRuntimeException(ResultCodeConstant.CHANGE_SIGN_FAILED);
                }
                //更新完成次数
                if (!signResult.isEmptyValue() && signResult.getValue().getPassCount() > 0) {
                    FlagRequestDto flagRequestDto = new FlagRequestDto();
                    flagRequestDto.setId(flagShareRequestDto.getFlagId());
                    flagRequestDto.setUserId(flagShareRequestDto.getUserId());
                    flagRequestDto.setFlagUpdateType(FlagUpdateTypeEnum.SIGN_PASS);
                    flagRequestDto.setPassCount(signResult.getValue().getPassCount());
                    ResultBase<UpdateFlagResponseDto> flagResult = flagHandler.updateFlag(flagRequestDto);
                    if (flagResult.isSuccess() && !flagResult.isEmptyValue() && flagResult.getValue().getFlagFinish()){
                        NoticeConfigRequestDto noticeConfig = new NoticeConfigRequestDto();
                        noticeConfig.setId(flagResult.getValue().getNoticeConfigId());
                        noticeConfigHandler.delete(noticeConfig);
                    }
                }
            }

            if (updateResultBase.isSuccess()) {
                return new ResultBase<FlagShareResponseDto>().success();
            } else {
                return new ResultBase<FlagShareResponseDto>().failed(updateResultBase);
            }
        }
        return new ResultBase<FlagShareResponseDto>().success();
    }
}
