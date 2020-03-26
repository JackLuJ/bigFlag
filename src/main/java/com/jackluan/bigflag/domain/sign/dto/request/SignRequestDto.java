package com.jackluan.bigflag.domain.sign.dto.request;

import com.jackluan.bigflag.domain.sign.dto.base.SignBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:19
 */
@Data
@NoArgsConstructor
public class SignRequestDto extends SignBaseDto {

    List<SignApproverRequestDto> approverList;

}
