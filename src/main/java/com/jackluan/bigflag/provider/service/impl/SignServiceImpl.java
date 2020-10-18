package com.jackluan.bigflag.provider.service.impl;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.flag.FlagUpdateTypeEnum;
import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import com.jackluan.bigflag.common.utils.DateUtils;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.common.utils.WeChatUtils;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.file.dto.response.FileResponseDto;
import com.jackluan.bigflag.domain.file.handler.FileGroupHandler;
import com.jackluan.bigflag.domain.flag.dto.request.CreateSingInfoRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.CreateSingInfoResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.UpdateFlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.domain.notice.handler.NoticeConfigHandler;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import com.jackluan.bigflag.domain.sign.handler.SignHandler;
import com.jackluan.bigflag.provider.convert.FileShareConvert;
import com.jackluan.bigflag.provider.convert.FlagShareConvert;
import com.jackluan.bigflag.provider.convert.SignShareConvert;
import com.jackluan.bigflag.provider.dto.request.*;
import com.jackluan.bigflag.provider.dto.request.notice.NoticeSignCreateRequestDto;
import com.jackluan.bigflag.provider.dto.response.CreateSignShareResponseDto;
import com.jackluan.bigflag.provider.dto.request.notice.NoticeSignFinishRequestDto;
import com.jackluan.bigflag.provider.dto.response.SignShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.UserInfoShareResponseDto;
import com.jackluan.bigflag.provider.dto.response.wechat.SecCheckResponseDto;
import com.jackluan.bigflag.provider.service.INoticeService;
import com.jackluan.bigflag.provider.service.ISignService;
import com.jackluan.bigflag.provider.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SignServiceImpl implements ISignService {

    @Autowired
    private IUserService userService;

    @Autowired
    private SignHandler signHandler;

    @Autowired
    private FileGroupHandler fileGroupHandler;

    @Autowired
    private FlagHandler flagHandler;

    @Autowired
    private NoticeConfigHandler noticeConfigHandler;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private WeChatUtils weChatUtils;

    @Override
    public ResultBase<CreateSignShareResponseDto> createSign(CreateSignShareRequestDto createSignShareRequestDto) {
        createSignShareRequestDto.setUserId(UserUtils.getUser().getUserId());

        SecCheckResponseDto msgCheck = weChatUtils.msgSecCheck(createSignShareRequestDto.getDescription());
        if (msgCheck.getErrcode() != 0){
            return new ResultBase<CreateSignShareResponseDto>().failed(ResultCodeConstant.MSG_CHECK_NOT_PASS);
        }

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
        boolean updateFlagPerformance = true;
        if (flagResponse.getValue().getFlagType().getDaily()){
            int count = signHandler.queryTodayAchieveTimes(createSignShareRequestDto.getFlagId(), DateUtils.now());
            if (count > 0){
                updateFlagPerformance = false;
            }
        }

        //3.调用Sign领域 生成打卡信息
        SignRequestDto signRequestDto = SignShareConvert.INSTANCE.convertToDomainDto(createSignShareRequestDto);
        signRequestDto.setFileGroupId(fileGroupId);
        signRequestDto.setApproverList(SignShareConvert.INSTANCE.convertToDomainDto(flagResponse.getValue().getApproverList()));
        signRequestDto.setThreshold(flagResponse.getValue().getThreshold());
        if (flagResponse.getValue().getFlagType().getDaily()){
            signRequestDto.setDeadline(DateUtils.getTodayEnd());
        }else{
            signRequestDto.setDeadline(flagResponse.getValue().getDeadline());
        }
        ResultBase<SignResponseDto> signResultBase = signHandler.createSign(signRequestDto);
        if (!signResultBase.isSuccess() || signResultBase.isEmptyValue()){
            return new ResultBase<CreateSignShareResponseDto>().failed(signResultBase);
        }

        //4.打卡状态为通过更新flag信息
        if (signResultBase.getValue().getPassCount() > 0){
            if (updateFlagPerformance) {
                FlagRequestDto flagRequestDto = new FlagRequestDto();
                flagRequestDto.setId(createSignShareRequestDto.getFlagId());
                flagRequestDto.setUserId(createSignShareRequestDto.getUserId());
                flagRequestDto.setFlagUpdateType(FlagUpdateTypeEnum.SIGN_CHANGE);
                flagRequestDto.setPassCount(signResultBase.getValue().getPassCount());
                ResultBase<UpdateFlagResponseDto> flagResult = flagHandler.updateFlag(flagRequestDto);
            }
        }else{
            NoticeSignCreateRequestDto noticeSignCreate = new NoticeSignCreateRequestDto();
            noticeSignCreate.setSignId(signResultBase.getValue().getId());
            noticeSignCreate.setUserId(createSignShareRequestDto.getUserId());
            noticeSignCreate.setFlagId(createSignShareRequestDto.getFlagId());
            noticeService.noticeSignCreate(noticeSignCreate);
        }

        CreateSignShareResponseDto responseDto = new CreateSignShareResponseDto();
        responseDto.setSignId(signResultBase.getValue().getId());
        return new ResultBase<CreateSignShareResponseDto>().success(responseDto);
    }

    @Override
    public ResultBase<Page<SignShareResponseDto>> querySign(Page<SignShareRequestDto> signShareRequestDtoPage) {
        signShareRequestDtoPage.getCondition().setUserId(UserUtils.getUser().getUserId());

        //校验flag
        FlagRequestDto requestDto = new FlagRequestDto();
        requestDto.setId(signShareRequestDtoPage.getCondition().getFlagId());
        ResultBase<List<FlagResponseDto>> flagResultBase =  flagHandler.queryFlagList(requestDto);
        if (!flagResultBase.isSuccess() || CollectionUtils.isEmpty(flagResultBase.getValue())){
            return new ResultBase<Page<SignShareResponseDto>>().failed(ResultCodeConstant.FLAG_NOT_EXIST_FAILED, "flag not exist");
        }

        //设置查询sign参数
        QueryTypeEnum queryType = QueryTypeEnum.APPROVE;
        if (flagResultBase.getValue().get(0).getUserId() == UserUtils.getUser().getUserId()){
            queryType = QueryTypeEnum.OWN;
        }
        signShareRequestDtoPage.getCondition().setQueryType(queryType);
        signShareRequestDtoPage.getCondition().setUserId(UserUtils.getUser().getUserId());

        //查询sign
        Page<SignRequestDto> responseDtoPage = SignShareConvert.INSTANCE.convertToDomainDto(signShareRequestDtoPage);
        ResultBase<Page<SignResponseDto>> resultBase = signHandler.querySignListPage(responseDtoPage);
        if (!resultBase.isSuccess()){
            return new ResultBase<Page<SignShareResponseDto>>().failed(resultBase);
        }

        Page<SignShareResponseDto> response = SignShareConvert.INSTANCE.convertToShareDto(resultBase.getValue());


        //处理返回信息
        if(!CollectionUtils.isEmpty(response.getResults())){
            QueryTypeEnum finalQueryType = queryType;
            response.getResults().forEach(signResponse->{
                signResponse.setQueryType(finalQueryType);

                //填充sign图片
                List<String> fileUrlList = new ArrayList<>();
                FileGroupRequestDto fileGroupRequestDto = new FileGroupRequestDto();
                fileGroupRequestDto.setUserId(signResponse.getUserId());
                fileGroupRequestDto.setId(signResponse.getFileGroupId());
                ResultBase<FileGroupResponseDto> fileResultBase = fileGroupHandler.queryFileGroup(fileGroupRequestDto);
                if (fileResultBase.isSuccess() && fileResultBase.getValue() != null && !CollectionUtils.isEmpty(fileResultBase.getValue().getFileList())) {
                    fileUrlList = fileResultBase.getValue().getFileList().stream().map(FileResponseDto::getUrl).collect(Collectors.toList());
                }
                signResponse.setFileUrlList(fileUrlList);

                //填充审批者信息
                if (CollectionUtils.isEmpty(signResponse.getApproverList())){
                    return;
                }
                signResponse.getApproverList().forEach(approver->{
                    if (null == approver.getUserId()) {
                        return;
                    }
                    UserShareRequestDto userRequest = new UserShareRequestDto();
                    userRequest.setId(approver.getUserId());
                    ResultBase<UserInfoShareResponseDto> userResponse = userService.queryUser(userRequest);
                    if (!userResponse.isSuccess() || userResponse.isEmptyValue()){
                        return;
                    }

                    approver.setNickname(userResponse.getValue().getNickname());
                    approver.setUrl(userResponse.getValue().getUrl());
                });

            });
        }

        return new ResultBase<Page<SignShareResponseDto>>().success(response);
    }

    @Override
    public ResultBase<Void> approveSign(ApproveSignShareRequestDto approveSignShareRequestDto) {
        approveSignShareRequestDto.setUserId(UserUtils.getUser().getUserId());
        //1. approve
        SignApproverRequestDto signApproverRequestDto = SignShareConvert.INSTANCE.convertToDomainDto(approveSignShareRequestDto);
        ResultBase<SignResponseDto> signResult = signHandler.approveSign(signApproverRequestDto);
        if (!signResult.isSuccess()){
            return new ResultBase<Void>().failed(signResult);
        }

        //通知flag创建者 有人进行审核操作
        NoticeSignFinishRequestDto noticeSignFinish = new NoticeSignFinishRequestDto();
        noticeSignFinish.setApproverId(approveSignShareRequestDto.getUserId());
        noticeSignFinish.setFlagId(signResult.getValue().getFlagId());
        noticeSignFinish.setUserId(signResult.getValue().getUserId());
        noticeSignFinish.setResultType(approveSignShareRequestDto.getResultType());
        noticeSignFinish.setStatus(signResult.getValue().getStatus());
        noticeSignFinish.setSignId(approveSignShareRequestDto.getSignId());
        noticeService.noticeSignFinish(noticeSignFinish);

        FlagRequestDto queryFlagRequestDto = new FlagRequestDto();
        queryFlagRequestDto.setId(signResult.getValue().getFlagId());
        ResultBase<List<FlagResponseDto>> resultBase = flagHandler.queryFlagList(queryFlagRequestDto);
        if (!resultBase.isSuccess() || resultBase.isEmptyValue()) {
            return new ResultBase<Void>().failed(ResultCodeConstant.FLAG_NOT_EXIST_FAILED);
        }

        boolean updateFlagPerformance = true;
        if (resultBase.getValue().get(0).getFlagType().getDaily() && signResult.getValue().getAlreadyAchieveTimes() > 0){
            updateFlagPerformance = false;
        }

        //更新完成次数
        if (!signResult.isEmptyValue() && signResult.getValue().getPassCount() > 0 && updateFlagPerformance) {
            FlagRequestDto flagRequestDto = new FlagRequestDto();
            flagRequestDto.setId(signResult.getValue().getFlagId());
            flagRequestDto.setUserId(signResult.getValue().getUserId());
            flagRequestDto.setFlagUpdateType(FlagUpdateTypeEnum.SIGN_CHANGE);
            flagRequestDto.setPassCount(signResult.getValue().getPassCount());
            ResultBase<UpdateFlagResponseDto> flagResult = flagHandler.updateFlag(flagRequestDto);
        }
        return new ResultBase<Void>().success();
    }

    @Override
    public ResultBase<Void> delete(DeleteSignRequestDto deleteSignRequestDto) {
        SignRequestDto signRequestDto = new SignRequestDto();
        signRequestDto.setId(deleteSignRequestDto.getId());

        //查询sign
        List<SignResponseDto> signResponseList = signHandler.queryList(signRequestDto);
        if (CollectionUtils.isEmpty(signResponseList)){
            return new ResultBase<Void>().failed(ResultCodeConstant.CAN_NOT_FIND_SIGN);
        }

        SignResponseDto signResponseDto = signResponseList.get(0);
        //如果 是通过状态 校验是否为当日唯一的一次打卡
        if (SignStatusEnum.PASSED == signResponseDto.getStatus()){
            //查询当日完成次数
            int count = signHandler.queryTodayAchieveTimes(signResponseDto.getFlagId(), signResponseDto.getAchieveDate());
            if (count == 1){
                //只有一条记录减少一天打卡天数
                FlagRequestDto flagRequestDto = new FlagRequestDto();
                flagRequestDto.setId(signResponseDto.getFlagId());
                flagRequestDto.setUserId(signResponseDto.getUserId());
                flagRequestDto.setFlagUpdateType(FlagUpdateTypeEnum.SIGN_CHANGE);
                flagRequestDto.setPassCount(-1);
                ResultBase<UpdateFlagResponseDto> flagResult = flagHandler.updateFlag(flagRequestDto);
            }
        }

        //删除sign
        signHandler.delete(signRequestDto);
        return new ResultBase<Void>().success();
    }
}
