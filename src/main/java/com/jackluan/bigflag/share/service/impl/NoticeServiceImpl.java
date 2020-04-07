package com.jackluan.bigflag.share.service.impl;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.enums.base.WeChatNoticeTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import com.jackluan.bigflag.common.utils.DateUtils;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.domain.flag.handler.FlagHandler;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import com.jackluan.bigflag.domain.notice.handler.NoticeConfigHandler;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignApproverResponseDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import com.jackluan.bigflag.domain.sign.handler.SignApproverHandler;
import com.jackluan.bigflag.domain.sign.handler.SignHandler;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import com.jackluan.bigflag.share.dto.request.SendOaMessageRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeBingSuccessRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeInviteSuccessRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeSignCreateRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeSignFinishRequestDto;
import com.jackluan.bigflag.share.service.INoticeService;
import com.jackluan.bigflag.share.service.IWeChatNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 12:05
 */
@Slf4j
@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeConfigHandler noticeConfigHandler;

    @Autowired
    private FlagHandler flagHandler;

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private SignHandler signHandler;

    @Autowired
    private SignApproverHandler signApproverHandler;

    @Autowired
    private IWeChatNoticeService weChatNoticeService;

    @Override
    public void batchNoticeUser() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm");
        String dateStr = format.format(date);
        NoticeConfigRequestDto noticeConfig = new NoticeConfigRequestDto();
        noticeConfig.setNoticeDate(dateStr);
        List<NoticeConfigResponseDto> responseList = noticeConfigHandler.queryList(noticeConfig);
        if (CollectionUtils.isEmpty(responseList)) {
            return;
        }

        responseList.forEach(noticeConfigResponse -> {
            FlagRequestDto flag = new FlagRequestDto();
            flag.setNoticeConfigId(noticeConfigResponse.getId());
            ResultBase<List<FlagResponseDto>> flagResult = flagHandler.queryFlagList(flag);
            if (!flagResult.isSuccess() || CollectionUtils.isEmpty(flagResult.getValue())) {
                return;
            }
            FlagResponseDto flagResponse = flagResult.getValue().get(0);
            String flagName = flagResponse.getTitle();

            UserRequestDto user = new UserRequestDto();
            user.setId(flagResponse.getUserId());
            ResultBase<List<UserResponseDto>> userResult = userHandler.queryUser(user);
            if (!userResult.isSuccess() || CollectionUtils.isEmpty(userResult.getValue())) {
                return;
            }
            UserResponseDto userResponse = userResult.getValue().get(0);

            Map<String, String> paramMap = new HashMap<>(16);
            paramMap.put("first", "别忘了在你立的flag呀！");
            paramMap.put("keyword1", flagName);
            paramMap.put("keyword2", "进行中");
            paramMap.put("remark", "点击去上传凭证完成打卡吧！>");

            SendOaMessageRequestDto sendOaMessage = new SendOaMessageRequestDto();
            sendOaMessage.setMsgType(WeChatNoticeTypeEnum.NOTICE_SIGN);
            sendOaMessage.setNoticeConfigId(noticeConfigResponse.getId());
            sendOaMessage.setParamMap(paramMap);
            sendOaMessage.setOaOpenId(userResponse.getOaOpenId());
            sendOaMessage.setUserId(userResponse.getId());

            weChatNoticeService.sendMsg(sendOaMessage);
        });
    }

    @Override
    public void noticeSignFinish(NoticeSignFinishRequestDto noticeSignFinish) {
        FlagRequestDto flag = new FlagRequestDto();
        flag.setId(noticeSignFinish.getFlagId());
        ResultBase<List<FlagResponseDto>> flagResult = flagHandler.queryFlagList(flag);
        if (!flagResult.isSuccess() || CollectionUtils.isEmpty(flagResult.getValue())) {
            return;
        }

        UserRequestDto user = new UserRequestDto();
        user.setId(noticeSignFinish.getUserId());
        ResultBase<List<UserResponseDto>> userResult = userHandler.queryUser(user);
        if (!userResult.isSuccess() || CollectionUtils.isEmpty(userResult.getValue())) {
            return;
        }

        user.setId(noticeSignFinish.getApproverId());
        ResultBase<List<UserResponseDto>> approverResult = userHandler.queryUser(user);
        if (!approverResult.isSuccess() || CollectionUtils.isEmpty(approverResult.getValue())) {
            return;
        }

        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("first", "你上传的凭证已审核完成啦！");
        paramMap.put("keyword1", flagResult.getValue().get(0).getTitle());
        paramMap.put("keyword2", approverResult.getValue().get(0).getNickname());
        paramMap.put("keyword3", noticeSignFinish.getResultType().getDesc());
        paramMap.put("keyword4", "凭证" + noticeSignFinish.getStatus().getDesc());
        paramMap.put("remark", "点击查看详情>");

        SendOaMessageRequestDto sendOaMessage = new SendOaMessageRequestDto();
        sendOaMessage.setMsgType(WeChatNoticeTypeEnum.SIGN_CHECKED);
        sendOaMessage.setParamMap(paramMap);
        sendOaMessage.setOaOpenId(userResult.getValue().get(0).getOaOpenId());
        sendOaMessage.setUserId(noticeSignFinish.getUserId());
        weChatNoticeService.sendMsg(sendOaMessage);
    }

    @Override
    public void batchNoticeApprove() {
        Date dateStart = DateUtils.getBeforeMinuteStart(new Date(), 2, 10);
        Date dateEnd = DateUtils.getBeforeMinuteEnd(new Date(), 2, 0);
        SignRequestDto signRequestDto = new SignRequestDto();
        signRequestDto.setStartTime(dateStart);
        signRequestDto.setEndTime(dateEnd);
        signRequestDto.setStatus(SignStatusEnum.UNDER_REVIEW);
        List<SignResponseDto> signList = signHandler.queryListByTimeRange(signRequestDto);
        if (CollectionUtils.isEmpty(signList)) {
            return;
        }

        signList.forEach(sign -> {
            FlagRequestDto flag = new FlagRequestDto();
            flag.setId(sign.getFlagId());
            ResultBase<List<FlagResponseDto>> flagResult = flagHandler.queryFlagList(flag);
            if (!flagResult.isSuccess() || CollectionUtils.isEmpty(flagResult.getValue())) {
                return;
            }

            UserRequestDto user = new UserRequestDto();
            user.setId(sign.getUserId());
            ResultBase<List<UserResponseDto>> userResult = userHandler.queryUser(user);
            if (!userResult.isSuccess() || CollectionUtils.isEmpty(userResult.getValue())) {
                return;
            }

            SignApproverRequestDto signApproverRequest = new SignApproverRequestDto();
            signApproverRequest.setSignId(sign.getId());
            ResultBase<List<SignApproverResponseDto>> approverResult = signApproverHandler.queryList(signApproverRequest);
            if (!approverResult.isSuccess() || CollectionUtils.isEmpty(approverResult.getValue())) {
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < approverResult.getValue().size(); i++) {
                UserRequestDto approver = new UserRequestDto();
                approver.setId(approverResult.getValue().get(i).getApproverUserId());
                ResultBase<List<UserResponseDto>> info = userHandler.queryUser(user);
                if (!info.isSuccess() || CollectionUtils.isEmpty(info.getValue())) {
                    stringBuilder.append(info.getValue().get(0).getNickname());
                    stringBuilder.append("  ");
                }
                if (i == 3) {
                    break;
                }
            }

            Map<String, String> paramMap = new HashMap<>(16);
            paramMap.put("first", "你的凭证尚未审核通过~");
            paramMap.put("keyword1", flagResult.getValue().get(0).getTitle());
            paramMap.put("keyword2", sign.getStatus().getDesc());
            paramMap.put("keyword3", stringBuilder.toString());
            paramMap.put("remark", "赶快叫小伙伴帮你审核一下吧~>");

            SendOaMessageRequestDto sendOaMessage = new SendOaMessageRequestDto();
            sendOaMessage.setMsgType(WeChatNoticeTypeEnum.SERVER_STATUS);
            sendOaMessage.setParamMap(paramMap);
            sendOaMessage.setOaOpenId(userResult.getValue().get(0).getOaOpenId());
            sendOaMessage.setUserId(sign.getUserId());
            weChatNoticeService.sendMsg(sendOaMessage);
        });
    }

    @Override
    public void noticeInviteSuccess(NoticeInviteSuccessRequestDto noticeInviteSuccess) {

        UserRequestDto user = new UserRequestDto();
        user.setId(noticeInviteSuccess.getUserId());
        ResultBase<List<UserResponseDto>> userResult = userHandler.queryUser(user);
        if (!userResult.isSuccess() || CollectionUtils.isEmpty(userResult.getValue())) {
            return;
        }

        user.setId(noticeInviteSuccess.getInviteUserId());
        ResultBase<List<UserResponseDto>> approverResult = userHandler.queryUser(user);
        if (!approverResult.isSuccess() || CollectionUtils.isEmpty(approverResult.getValue())) {
            return;
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("first", "有人接受了你得邀请，快去小程序里确认吧~");
        paramMap.put("keyword1", approverResult.getValue().get(0).getNickname());
        paramMap.put("keyword2", approverResult.getValue().get(0).getMobile());
        paramMap.put("keyword3", format.format(new Date()));
        paramMap.put("remark", "点击确认关系~>");

        SendOaMessageRequestDto sendOaMessage = new SendOaMessageRequestDto();
        sendOaMessage.setMsgType(WeChatNoticeTypeEnum.INVITE_SUCCESSFUL);
        sendOaMessage.setParamMap(paramMap);
        sendOaMessage.setOaOpenId(userResult.getValue().get(0).getOaOpenId());
        sendOaMessage.setUserId(noticeInviteSuccess.getUserId());
        weChatNoticeService.sendMsg(sendOaMessage);
    }

    @Override
    public void noticeSignCreate(NoticeSignCreateRequestDto noticeSignCreate) {
        UserRequestDto user = new UserRequestDto();
        user.setId(noticeSignCreate.getUserId());
        ResultBase<List<UserResponseDto>> userResult = userHandler.queryUser(user);
        if (!userResult.isSuccess() || CollectionUtils.isEmpty(userResult.getValue())) {
            return;
        }

        FlagRequestDto flag = new FlagRequestDto();
        flag.setId(noticeSignCreate.getFlagId());
        ResultBase<List<FlagResponseDto>> flagResult = flagHandler.queryFlagList(flag);
        if (!flagResult.isSuccess() || CollectionUtils.isEmpty(flagResult.getValue())) {
            return;
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("first", "hello，你有一条flag凭证待审核");
        paramMap.put("keyword1", flagResult.getValue().get(0).getAchieveConfigType().getDesc()+"的flag凭证");
        paramMap.put("keyword2", userResult.getValue().get(0).getNickname());
        paramMap.put("keyword3", flagResult.getValue().get(0).getTitle());
        paramMap.put("keyword4", format.format(new Date()));
        paramMap.put("remark", "快去帮他审核凭证吧>");

        SendOaMessageRequestDto sendOaMessage = new SendOaMessageRequestDto();
        sendOaMessage.setMsgType(WeChatNoticeTypeEnum.UNDER_APPROVE);
        sendOaMessage.setParamMap(paramMap);
        sendOaMessage.setOaOpenId(userResult.getValue().get(0).getOaOpenId());
        sendOaMessage.setUserId(noticeSignCreate.getUserId());
        weChatNoticeService.sendMsg(sendOaMessage);
    }

    @Override
    public void noticeBindSuccess(NoticeBingSuccessRequestDto noticeBingSuccess) {
        UserRequestDto user = new UserRequestDto();
        user.setId(noticeBingSuccess.getUserId());
        ResultBase<List<UserResponseDto>> userResult = userHandler.queryUser(user);
        if (!userResult.isSuccess() || CollectionUtils.isEmpty(userResult.getValue())) {
            return;
        }

        FlagRequestDto flag = new FlagRequestDto();
        flag.setId(noticeBingSuccess.getFlagId());
        ResultBase<List<FlagResponseDto>> flagResult = flagHandler.queryFlagList(flag);
        if (!flagResult.isSuccess() || CollectionUtils.isEmpty(flagResult.getValue())) {
            return;
        }

        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("first", "绑定成功！你成功加入了\""+flagResult.getValue().get(0).getTitle()+"\"的监督团队");
        paramMap.put("keyword1", userResult.getValue().get(0).getNickname());
        paramMap.put("keyword2", flagResult.getValue().get(0).getTitle());
        paramMap.put("remark", "点击查看详情>");

        SendOaMessageRequestDto sendOaMessage = new SendOaMessageRequestDto();
        sendOaMessage.setMsgType(WeChatNoticeTypeEnum.BIND_SUCCESS);
        sendOaMessage.setParamMap(paramMap);
        sendOaMessage.setOaOpenId(userResult.getValue().get(0).getOaOpenId());
        sendOaMessage.setUserId(noticeBingSuccess.getUserId());
        weChatNoticeService.sendMsg(sendOaMessage);
    }
}
