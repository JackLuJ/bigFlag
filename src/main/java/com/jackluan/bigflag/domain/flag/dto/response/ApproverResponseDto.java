package com.jackluan.bigflag.domain.flag.dto.response;

import com.jackluan.bigflag.domain.flag.dto.base.ApproverBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 17:43
 */
@Data
@NoArgsConstructor
public class ApproverResponseDto extends ApproverBaseDto {

    private Long fileGroupId;

}
