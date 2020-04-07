package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.share.dto.request.notice.NoticeBingSuccessRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeInviteSuccessRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeSignCreateRequestDto;
import com.jackluan.bigflag.share.dto.request.notice.NoticeSignFinishRequestDto;

/**
 * @Author: jack.luan
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
