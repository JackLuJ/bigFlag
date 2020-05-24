package com.jackluan.bigflag.domain.flag.handler;

import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.enums.base.YesNoEnum;
import com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagChangeTypeEnum;
import com.jackluan.bigflag.common.enums.flag.FlagStatusEnum;
import com.jackluan.bigflag.common.enums.flag.FlagTypeEnum;
import com.jackluan.bigflag.common.utils.DateUtils;
import com.jackluan.bigflag.domain.flag.convert.FlagConvert;
import com.jackluan.bigflag.domain.flag.convert.FlagTraceConvert;
import com.jackluan.bigflag.domain.flag.dto.base.ApproverBaseDto;
import com.jackluan.bigflag.domain.flag.dto.base.FlagBaseDto;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.CreateSingInfoRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagTraceRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.CreateSingInfoResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.UpdateFlagResponseDto;
import com.jackluan.bigflag.domain.flag.logic.ApproverLogic;
import com.jackluan.bigflag.domain.flag.logic.FlagLogic;
import com.jackluan.bigflag.domain.flag.logic.FlagTraceLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jack.luan
 * @Date: 2020/3/8 15:53
 */
@Component
public class FlagHandler {

    @Autowired
    private FlagLogic flagLogic;

    @Autowired
    private FlagTraceLogic flagTraceLogic;

    @Autowired
    private ApproverLogic approverLogic;

    public ResultBase<FlagResponseDto> createFlag(FlagRequestDto requestDto) {
        FlagResponseDto responseDto = new FlagResponseDto();
        if (requestDto.getFlagType() == FlagTypeEnum.DAILY_FLAG && requestDto.getDeadline() != null) {
            requestDto.setThreshold(getThreshold(requestDto.getDeadline()));
        }

        requestDto.setSequenceNo(1);
        requestDto.setPerformance(0);
        long flagId = flagLogic.createFlag(requestDto);
        if (flagId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FLAG_FAILED);
        }

        requestDto.setId(flagId);
        FlagTraceRequestDto flagTraceRequestDto = FlagTraceConvert.INSTANCE.convertFlag(requestDto);
        long flagTraceId = flagTraceLogic.createFlagTrace(flagTraceRequestDto);
        if (flagTraceId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FLAG_TRACE_FAILED);
        }

        responseDto.setId(flagId);
        return new ResultBase<FlagResponseDto>().success(responseDto);
    }

    public ResultBase<List<FlagResponseDto>> queryFlagList(FlagRequestDto requestDto) {
        return new ResultBase<List<FlagResponseDto>>().success(flagLogic.queryFlagList(requestDto));
    }

    public ResultBase<Page<FlagResponseDto>> queryFlagListPage(Page<FlagRequestDto> requestDto) {

        List<FlagResponseDto> responseList;
        switch (requestDto.getCondition().getQueryType()) {
            case OWN:
                requestDto.getCondition().setStatusList(FlagStatusEnum.getStatusListByCategory(requestDto.getCondition().getFlagCategory()));
                responseList = queryOwnFlagList(requestDto);
                break;
            case APPROVE:
                responseList = queryApproveFlagList(requestDto);
                break;
            default:
                return new ResultBase<Page<FlagResponseDto>>().success();
        }

        Boolean showApprover = requestDto.getCondition().getShowApprover();
        if (showApprover != null && showApprover) {
            responseList.forEach(flagResponseDto -> {
                ApproverRequestDto approverRequestDto = new ApproverRequestDto();
                approverRequestDto.setFlagId(flagResponseDto.getId());
                approverRequestDto.setStatus(ApproverStatusEnum.EFFECTIVE);
                List<ApproverResponseDto> approverList = approverLogic.queryApproverList(approverRequestDto);
                flagResponseDto.setApproverList(approverList);
            });
        }

        Page<FlagResponseDto> page = Page.mapper(requestDto);
        page.setResults(responseList);
        return new ResultBase<Page<FlagResponseDto>>().success(page);
    }

    public ResultBase<CreateSingInfoResponseDto> getSignInfo(CreateSingInfoRequestDto createSingInfoRequestDto) {
        return new ResultBase<CreateSingInfoResponseDto>().success(getSignInfoBase(createSingInfoRequestDto));
    }

    public ResultBase<UpdateFlagResponseDto> updateFlag(FlagRequestDto flagRequestDto) {
        UpdateFlagResponseDto updateFlagResponseDto = new UpdateFlagResponseDto();

        if (null == flagRequestDto.getFlagUpdateType()) {
            throw new BigFlagRuntimeException(ResultCodeConstant.FLAG_UPDATE_TYPE_NOT_SET);
        }

        FlagRequestDto flagRequest = new FlagRequestDto();
        flagRequest.setId(flagRequestDto.getId());
        flagRequest.setUserId(flagRequestDto.getUserId());
        flagRequest.setStatus(FlagStatusEnum.IN_PROGRESS);
        List<FlagResponseDto> flagList = flagLogic.queryFlagList(flagRequest);
        if (CollectionUtils.isEmpty(flagList)) {
            throw new BigFlagRuntimeException(ResultCodeConstant.FIND_FLAG_FAILED);
        }

        switch (flagRequestDto.getFlagUpdateType()) {
            case SIGN_CHANGE:
                updateFlagResponseDto.setFlagFinish(updateWhenSignPass(flagRequestDto));
                break;
            case FLAG_UPDATE:
                updateFlagResponseDto.setChangeTypes(flagChange(flagRequestDto, flagList.get(0)));
                break;
            default:
                break;
        }

        FlagRequestDto requestDto = new FlagRequestDto();
        requestDto.setId(flagRequestDto.getId());
        List<FlagResponseDto> resultList = flagLogic.queryFlagList(requestDto);
        updateFlagResponseDto.setFlagStatusEnum(resultList.get(0).getStatus());
        updateFlagResponseDto.setNoticeConfigId(flagList.get(0).getNoticeConfigId());
        updateFlagResponseDto.setFlagType(resultList.get(0).getFlagType());
        return new ResultBase<UpdateFlagResponseDto>().success(updateFlagResponseDto);
    }

    public List<Long> batchExpireFlag(){
        Date now = DateUtils.getTodayStart();
        FlagRequestDto requestDto = new FlagRequestDto();
        requestDto.setDeadline(now);
        List<FlagResponseDto> responseList = flagLogic.selectByDeadline(requestDto);
        if (responseList == null){
            return null;
        }
        List<Long> noticeConfigIds = responseList.stream().map(FlagBaseDto::getNoticeConfigId).collect(Collectors.toList());

        requestDto.setStatus(FlagStatusEnum.OVER_DUE);
        flagLogic.updateByDeadline(requestDto);
        return noticeConfigIds;
    }

    private int getThreshold(Date deadline) {
        long deadLine = deadline.getTime();
        return Long.valueOf((deadLine - System.currentTimeMillis()) / (24 * 60 * 60 * 1000) + 1).intValue();
    }

    private List<FlagChangeTypeEnum> flagChange(FlagRequestDto flagRequestDto, FlagResponseDto oldFlag) {
        UpdateFlagResponseDto responseDto = new UpdateFlagResponseDto();

        //1.查询现存approverList  2.更新现存flag trace approverInfo  3.更新approver
        this.updateApprover(flagRequestDto, oldFlag);

        //生成新的Trace对象
        FlagTraceRequestDto flagTraceRequestDto = FlagTraceConvert.INSTANCE.convertFlag(oldFlag);
        flagTraceRequestDto.setApproverInfo(null);

        //生成本次变更的类型集合
        boolean checkFinish = false;
        List<FlagChangeTypeEnum> changeTypes = new ArrayList<>();
        if (oldFlag.getFlagType() == FlagTypeEnum.DAILY_FLAG && flagRequestDto.getDeadline() != null && (oldFlag.getDeadline() == null || flagRequestDto.getDeadline().getTime() != oldFlag.getDeadline().getTime())) {
            changeTypes.add(FlagChangeTypeEnum.DEADLINE);
            flagTraceRequestDto.setDeadline(flagRequestDto.getDeadline());
            flagTraceRequestDto.setThreshold(getThreshold(flagRequestDto.getDeadline()));
            checkFinish = true;
        }
        if (oldFlag.getFlagType() == FlagTypeEnum.TIMES_FLAG && flagRequestDto.getThreshold() != null && flagRequestDto.getThreshold().intValue() != oldFlag.getThreshold().intValue()) {
            changeTypes.add(FlagChangeTypeEnum.THRESHOLD);
            flagTraceRequestDto.setThreshold(flagRequestDto.getThreshold());
            checkFinish = true;
        }
        if (flagRequestDto.getAchieveConfigType() != null && flagRequestDto.getAchieveConfigType() != oldFlag.getAchieveConfigType()) {
            changeTypes.add(FlagChangeTypeEnum.ACHIEVE_CONFIG_TYPE);
            flagTraceRequestDto.setAchieveConfigType(flagRequestDto.getAchieveConfigType());
        }
        if (!CollectionUtils.isEmpty(flagRequestDto.getApproverList())) {
            changeTypes.add(FlagChangeTypeEnum.APPROVER);
        }
        if (flagRequestDto.getAchieve() != null) {
            changeTypes.add(FlagChangeTypeEnum.TERMINATION);
            if (YesNoEnum.YES == flagRequestDto.getAchieve()){
                flagTraceRequestDto.setStatus(FlagStatusEnum.ACHIEVE);
            }else {
                flagTraceRequestDto.setStatus(FlagStatusEnum.NOT_ACHIEVE);
            }
            checkFinish = false;
        }

        //判断Flag是否成功 flag 种类是force的才会变更为完成
        if (checkFinish && flagTraceRequestDto.getPerformance() >= flagTraceRequestDto.getThreshold() && oldFlag.getFlagType().getForce()) {
            flagTraceRequestDto.setStatus(FlagStatusEnum.FINISHED);
        }

        //生成新的trace，版本号+1
        flagTraceRequestDto.setSequenceNo(flagTraceRequestDto.getSequenceNo() + 1);
        long traceId = flagTraceLogic.createFlagTrace(flagTraceRequestDto);
        if (traceId < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.CREATE_FLAG_TRACE_FAILED);
        }

        FlagRequestDto createDto = FlagConvert.INSTANCE.convert(flagTraceRequestDto);
        createDto.setId(oldFlag.getId());
        createDto.setSequenceNo(oldFlag.getSequenceNo());
        //如果没变化触发事务回滚
        int count = flagLogic.queryCount(createDto);
        if (count == 1 && !changeTypes.contains(FlagChangeTypeEnum.APPROVER)){
            throw new BigFlagRuntimeException(ResultCodeConstant.FLAG_NOT_CHANGE);
        }

        //更新flag
        createDto.setSequenceNo(flagTraceRequestDto.getSequenceNo());
        flagLogic.updateFlag(createDto);
        return changeTypes;
    }

    private CreateSingInfoResponseDto getSignInfoBase(CreateSingInfoRequestDto createSingInfoRequestDto){
        FlagRequestDto requestDto = FlagConvert.INSTANCE.convert(createSingInfoRequestDto);
        requestDto.setStatus(FlagStatusEnum.IN_PROGRESS);
        List<FlagResponseDto> responseList = flagLogic.queryFlagList(requestDto);
        if (CollectionUtils.isEmpty(responseList)) {
            throw new BigFlagRuntimeException(ResultCodeConstant.FIND_FLAG_FAILED);
        }

        FlagResponseDto flag = responseList.get(0);

        ApproverRequestDto approverRequestDto = new ApproverRequestDto();
        approverRequestDto.setFlagId(createSingInfoRequestDto.getFlagId());
        approverRequestDto.setStatus(ApproverStatusEnum.EFFECTIVE);
        List<ApproverResponseDto> approverList = approverLogic.queryApproverList(approverRequestDto);

        BigDecimal sumScore = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(approverList)) {
            sumScore = approverList.stream().map(item -> item.getScore() == null ? BigDecimal.ZERO : new BigDecimal(item.getScore())).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        BigDecimal factor = flag.getAchieveConfigType().getFactor();
        int scale = flag.getAchieveConfigType().getScale();
        RoundingMode roundingMode = flag.getAchieveConfigType().getRoundingMode();
        int threshold = 1;
        if (-1 != factor.intValue()) {
            threshold = sumScore.divide(factor, scale, roundingMode).intValue();
        }
        if (threshold < 1){
            threshold = 1;
        }

        CreateSingInfoResponseDto response = new CreateSingInfoResponseDto();
        response.setThreshold(threshold);
        response.setApproverList(approverList);
        response.setDeadline(DateUtils.getTodayEnd());
        response.setFlagType(flag.getFlagType());
        return response;
    }

    private void updateApprover(FlagRequestDto flagRequestDto, FlagResponseDto oldFlag) {
        //1.查询现存approverList
        ApproverRequestDto approverRequestDto = new ApproverRequestDto();
        approverRequestDto.setFlagId(flagRequestDto.getId());
        approverRequestDto.setStatus(ApproverStatusEnum.EFFECTIVE);
        List<ApproverResponseDto> approverList = approverLogic.queryApproverList(approverRequestDto);

        //2.更新现存flag trace approverInfo
        List<Long> userIds = new ArrayList<>();
        if (!StringUtils.isEmpty(approverList)) {
            userIds.addAll(approverList.stream().map(ApproverBaseDto::getUserId).collect(Collectors.toList()));
        }
        JsonConverter converter = new JsonConverter();
        FlagTraceRequestDto requestDto = new FlagTraceRequestDto();
        requestDto.setFlagId(oldFlag.getId());
        requestDto.setSequenceNo(oldFlag.getSequenceNo());
        requestDto.setApproverInfo(converter.objToJson(userIds));
        flagTraceLogic.updateTrace(requestDto);

        //3.更新approver
        if (!CollectionUtils.isEmpty(flagRequestDto.getApproverList())) {
            flagRequestDto.getApproverList().forEach(approver -> {
                if (null == approver.getAddApprover()) {
                    return;
                } else if (YesNoEnum.NO == approver.getAddApprover()) {
                    ApproverRequestDto approverUpdate = new ApproverRequestDto();
                    approverUpdate.setId(approver.getId());
                    approverUpdate.setStatus(ApproverStatusEnum.DISABLED);
                    approverLogic.updateApprover(approverUpdate);
                }
            });
        }

    }


    private boolean updateWhenSignPass(FlagRequestDto flagRequestDto) {
        flagRequestDto.setPerformance(flagRequestDto.getPassCount());
        int count = flagLogic.increasePerformance(flagRequestDto);
        if (count < 1) {
            throw new BigFlagRuntimeException(ResultCodeConstant.INCREASE_FLAG_PERFORMANCE_FAILED);
        }

        //flag暂时没有完成状态
//        flagRequestDto.setStatus(FlagStatusEnum.FINISHED);
//        int successCount = flagLogic.updatePassFlag(flagRequestDto);
//        if (successCount > 0){
//            return true;
//        }else {
//            return false;
//        }

        return false;
    }

    private List<FlagResponseDto> queryOwnFlagList(Page<FlagRequestDto> requestDto) {
        int count = flagLogic.queryCountExtra(requestDto.getPageCondition());
        requestDto.setTotal(count);
        return flagLogic.queryFlagListExtra(requestDto.getCondition());
    }

    private List<FlagResponseDto> queryApproveFlagList(Page<FlagRequestDto> requestDto) {
        int count = flagLogic.queryApproveFlagCount(requestDto.getPageCondition());
        requestDto.setTotal(count);
        return flagLogic.queryApproveFlagList(requestDto.getCondition());
    }

}
