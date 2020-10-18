package com.jackluan.bigflag.provider.dto.request.notice;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/7 13:52
 */
@Data
@NoArgsConstructor
public class NoticeBingSuccessRequestDto {

    private Long userId;

    private Long flagId;

    private Long approverUserId;

}
