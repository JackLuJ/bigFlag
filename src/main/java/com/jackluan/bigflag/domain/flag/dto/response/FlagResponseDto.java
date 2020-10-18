package com.jackluan.bigflag.domain.flag.dto.response;

import com.jackluan.bigflag.domain.flag.dto.base.FlagBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 17:41
 */
@Data
@NoArgsConstructor
public class FlagResponseDto extends FlagBaseDto {

    private List<ApproverResponseDto> approverList;

}
