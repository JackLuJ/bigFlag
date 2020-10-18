package com.jackluan.bigflag.provider.service.impl;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.FlagChangeTypeEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagUpdateTypeEnum;
import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignChangeType;
import com.jackluan.bigflag.common.utils.DateUtils;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.common.utils.WeChatUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
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
import com.jackluan.bigflag.provider.convert.FlagShareConvert;
import com.jackluan.bigflag.provider.convert.NoticeShareConvert;
import com.jackluan.bigflag.provider.dto.request.*;
import com.jackluan.bigflag.provider.dto.response.FlagShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.UserInfoShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.wechat.SecCheckResponseDto;
import com.jackluan.bigflag.provider.service.IFlagService;
import com.jackluan.bigflag.provider.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: jeffery.luan
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

    @Autowired
    private WeChatUtils weChatUtils;

    @Override
    public ResultBase<FlagShareResponseDto> createFlag(FlagCreateShareRequestDto flagCreateShareRequestDto) {

        //微信文本校验
        StringBuilder sb = new StringBuilder();
        sb.append(flagCreateShareRequestDto.getTitle());
        sb.append(flagCreateShareRequestDto.getDescription());
        SecCheckResponseDto msgCheck = weChatUtils.msgSecCheck(sb.toString());
        if (msgCheck.getErrcode() != 0){
            return new ResultBase<FlagShareResponseDto>().failed(ResultCodeConstant.MSG_CHECK_NOT_PASS);
        }

        //设置如果用户输入了deadline，设置deadline为当天最后一秒
        flagCreateShareRequestDto.setUserId(UserUtils.getUser().getUserId());
        if (flagCreateShareRequestDto.getDeadline() != null){
            flagCreateShareRequestDto.setDeadline(DateUtils.getDayEnd(flagCreateShareRequestDto.getDeadline()));
        }

        //调用notice领域 生成notice配置 并返回Id
        NoticeConfigRequestDto noticeConfigRequestDto = NoticeShareConvert.INSTANCE.convertToNoticeConfig(flagCreateShareRequestDto);
        ResultBase<NoticeConfigResponseDto> noticeResult = noticeConfigHandler.createNoticeConfig(noticeConfigRequestDto);
        if (!noticeResult.isSuccess()) {
            return new ResultBase<FlagShareResponseDto>().failed(noticeResult);
        }

        //上传图片背景图
        Long fileGroupId = null;
        if (!StringUtils.isEmpty(flagCreateShareRequestDto.getFileUniqueCode())){
            FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
            fileGroupRequestDto.setFileUniqueCodeList(Collections.singletonList(flagCreateShareRequestDto.getFileUniqueCode()));
            fileGroupRequestDto.setUserId(flagCreateShareRequestDto.getUserId());
            ResultBase<FileGroupResponseDto> fileGroupResponse = fileGroupHandler.createFileGroup(fileGroupRequestDto);
            if (!fileGroupResponse.isSuccess() || fileGroupResponse.isEmptyValue()) {
                return new ResultBase<FlagShareResponseDto>().failed(fileGroupResponse);
            }
            fileGroupId = fileGroupResponse.getValue().getId();
        }

        //调用Flag领域生成flag
        FlagRequestDto flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto(flagCreateShareRequestDto);
        flagRequestDto.setNoticeConfigId(noticeResult.getValue().getId());
        flagRequestDto.setStatus(FlagStatusEnum.IN_PROGRESS);
        flagRequestDto.setFileGroupId(fileGroupId);
        ResultBase<FlagResponseDto> resultBase = flagHandler.createFlag(flagRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<FlagShareResponseDto>().failed(resultBase);
        }

        FlagShareResponseDto flagShareResponseDto = FlagShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());
        return new ResultBase<FlagShareResponseDto>().success(flagShareResponseDto);
    }

    @Override
    public ResultBase<Page<FlagShareResponseDto>> queryFlag(Page<FlagListShareRequestDto> flagListShareRequestDtoPage) {
        flagListShareRequestDtoPage.getCondition().setUserId(UserUtils.getUser().getUserId());

        //查询flagList
        Page<FlagRequestDto> flagRequestDto = FlagShareConvert.INSTANCE.convertToDomainDto2(flagListShareRequestDtoPage);
        flagRequestDto.getCondition().setShowApprover(true);
        ResultBase<Page<FlagResponseDto>> resultBase = flagHandler.queryFlagListPage(flagRequestDto);
        if (!resultBase.isSuccess()) {
            return new ResultBase<Page<FlagShareResponseDto>>().failed(resultBase);
        }
        Page<FlagShareResponseDto> dataList = FlagShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());

        //填充信息
        if (!CollectionUtils.isEmpty(dataList.getResults())) {
            dataList.getResults().forEach(flagShareResponseDto -> {

                //填充user
                UserShareRequestDto flagUserRequest = new UserShareRequestDto();
                flagUserRequest.setId(flagShareResponseDto.getUserId());
                ResultBase<UserInfoShareResponseDto> flagUserResponse = userService.queryUser(flagUserRequest);
                if (flagUserResponse.isSuccess() && !flagUserResponse.isEmptyValue()) {
                    flagShareResponseDto.setUserInfo(flagUserResponse.getValue());
                }

                //填充背景图
                if (null != flagShareResponseDto.getFileGroupId()) {
                    FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
                    fileGroupRequestDto.setUserId(flagShareResponseDto.getUserId());
                    fileGroupRequestDto.setId(flagShareResponseDto.getFileGroupId());
                    ResultBase<FileGroupResponseDto> fileResultBase = fileGroupHandler.queryFileGroup(fileGroupRequestDto);
                    if (fileResultBase.isSuccess() && fileResultBase.getValue() != null && !CollectionUtils.isEmpty(fileResultBase.getValue().getFileList())) {
                        flagShareResponseDto.setBgUrl(fileResultBase.getValue().getFileList().get(0).getUrl());
                    }
                }

                //填充审批人
                if (!CollectionUtils.isEmpty(flagShareResponseDto.getApproverList())) {
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
                }
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
    public ResultBase<FlagShareResponseDto> update(FlagUpdateRequestDto flagUpdateRequestDto) {
        flagUpdateRequestDto.setUserId(UserUtils.getUser().getUserId());
        if (flagUpdateRequestDto.getDeadline() != null){
            flagUpdateRequestDto.setDeadline(DateUtils.getDayEnd(flagUpdateRequestDto.getDeadline()));
        }
        //校验flag是否存在
        FlagRequestDto flagRequest = new FlagRequestDto();
        flagRequest.setId(flagUpdateRequestDto.getFlagId());
        flagRequest.setUserId(flagUpdateRequestDto.getUserId());
        ResultBase<List<FlagResponseDto>> flagResultBase = flagHandler.queryFlagList(flagRequest);
        if (!flagResultBase.isSuccess() || flagResultBase.isEmptyValue()) {
            return new ResultBase<FlagShareResponseDto>().failed(ResultCodeConstant.FLAG_NOT_EXIST_FAILED);
        }

        //判断flag状态是否允许更改
        FlagStatusEnum flagStatus = flagResultBase.getValue().get(0).getStatus();
        if (FlagStatusEnum.FINISHED == flagStatus ||FlagStatusEnum.ACHIEVE == flagStatus || FlagStatusEnum.NOT_ACHIEVE == flagStatus){
            return new ResultBase<FlagShareResponseDto>().failed(ResultCodeConstant.FLAG_NOT_EFFECTIVE);
        }

        //更新notice
        if (!StringUtils.isEmpty(flagUpdateRequestDto.getNoticeDate())) {
            NoticeConfigRequestDto noticeRequest = new NoticeConfigRequestDto();
            noticeRequest.setId(flagResultBase.getValue().get(0).getNoticeConfigId());
            noticeRequest.setNoticeDate(flagUpdateRequestDto.getNoticeDate());
            ResultBase<Void> noticeResult = noticeConfigHandler.updateNotice(noticeRequest);
            if (!noticeResult.isSuccess()) {
                return new ResultBase<FlagShareResponseDto>().failed(noticeResult);
            }
        }

        if (flagUpdateRequestDto.getDeadline() != null || flagUpdateRequestDto.getAchieveConfigType() != null ||flagUpdateRequestDto.getAchieve() != null) {
            //更新flag
            flagRequest = FlagShareConvert.INSTANCE.convertToDomainDto(flagUpdateRequestDto);
            flagRequest.setFlagUpdateType(FlagUpdateTypeEnum.FLAG_UPDATE);
            ResultBase<UpdateFlagResponseDto> updateResultBase = flagHandler.updateFlag(flagRequest);

            //flag 终止重算未完成的sign
            if (updateResultBase.isSuccess() && !CollectionUtils.isEmpty(updateResultBase.getValue().getChangeTypes()) && updateResultBase.getValue().getChangeTypes().contains(FlagChangeTypeEnum.TERMINATION)) {
                SignRequestDto signRequestDto = new SignRequestDto();
                signRequestDto.setFlagId(flagUpdateRequestDto.getFlagId());
                signRequestDto.setChangeType(SignChangeType.TERMINATION);
                ResultBase<SignResponseDto> signResult = signHandler.updateSign(signRequestDto);
                if (!signResult.isSuccess()) {
                    throw new BigFlagRuntimeException(ResultCodeConstant.CHANGE_SIGN_FAILED);
                }

                //删除已完成flag的notice
                NoticeConfigRequestDto noticeConfig = new NoticeConfigRequestDto();
                noticeConfig.setId(flagResultBase.getValue().get(0).getNoticeConfigId());
                noticeConfigHandler.delete(noticeConfig);
            }

            if (updateResultBase.isSuccess()) {
                return new ResultBase<FlagShareResponseDto>().success();
            } else {
                return new ResultBase<FlagShareResponseDto>().failed(updateResultBase);
            }
        }
        return new ResultBase<FlagShareResponseDto>().success();
    }

    @Override
    public void batchExpireFlag() {
        List<Long> configIds = flagHandler.batchExpireFlag();
        if (!CollectionUtils.isEmpty(configIds)) {
            configIds.forEach(configId -> {
                NoticeConfigRequestDto noticeConfig = new NoticeConfigRequestDto();
                noticeConfig.setId(configId);
                noticeConfigHandler.delete(noticeConfig);
            });
        }
    }
}
