package com.jackluan.bigflag.domain.sign.dto.request;

import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignChangeType;
import com.jackluan.bigflag.domain.sign.dto.base.SignBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:19
 */
@Data
@NoArgsConstructor
public class SignRequestDto extends SignBaseDto {

    private Boolean checkDailyTimes;

    private QueryTypeEnum queryType;

    private List<SignApproverRequestDto> approverList;

    private Date startTime;

    private Date endTime;

    private SignChangeType changeType;

    private SignApproverRequestDto delApprover;

}
