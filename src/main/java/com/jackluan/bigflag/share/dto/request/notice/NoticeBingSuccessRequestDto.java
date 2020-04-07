package com.jackluan.bigflag.share.dto.request.notice;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/4/7 13:52
 */
@Data
@NoArgsConstructor
public class NoticeBingSuccessRequestDto {

    private Long userId;

    private Long flagId;

}
