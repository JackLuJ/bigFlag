package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.provider.dto.request.notice.NoticeBingSuccessRequestDto;
import com.jackluan.bigflag.provider.dto.request.notice.NoticeInviteSuccessRequestDto;
import com.jackluan.bigflag.provider.dto.request.notice.NoticeSignCreateRequestDto;
import com.jackluan.bigflag.provider.dto.request.notice.NoticeSignFinishRequestDto;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/6 12:05
 */
public interface INoticeService {

    void batchNoticeUser();

    void noticeSignFinish(NoticeSignFinishRequestDto noticeSignFinish);

    void batchNoticeApprove();

    void noticeInviteSuccess(NoticeInviteSuccessRequestDto noticeInviteSuccess);

    void noticeSignCreate(NoticeSignCreateRequestDto noticeSignCreate);

    void noticeBindSuccess(NoticeBingSuccessRequestDto noticeBingSuccess);
}
