package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.annotation.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/18 23:20
 */
@Data
@NoArgsConstructor
public class QueryApproverStatusShareRequestDto {

    private Long userId;

    @NotEmpty
    private Long flagId;

}
