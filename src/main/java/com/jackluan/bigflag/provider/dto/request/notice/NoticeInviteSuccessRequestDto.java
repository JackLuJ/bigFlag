package com.jackluan.bigflag.provider.dto.request.notice;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/7 11:41
 */
@Data
@NoArgsConstructor
public class NoticeInviteSuccessRequestDto {

    private Long userId;

    private Long flagId;

    private Long inviteUserId;

}
