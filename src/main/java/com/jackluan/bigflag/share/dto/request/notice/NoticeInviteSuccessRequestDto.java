package com.jackluan.bigflag.share.dto.request.notice;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/7 11:41
 */
@Data
@NoArgsConstructor
public class NoticeInviteSuccessRequestDto {

    private Long userId;

    private Long inviteUserId;

}
