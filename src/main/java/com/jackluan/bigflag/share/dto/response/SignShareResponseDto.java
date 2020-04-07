package com.jackluan.bigflag.share.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.constant.SystemConstant;
import com.jackluan.bigflag.common.enums.flag.QueryTypeEnum;
import com.jackluan.bigflag.common.enums.sign.SignApproverResultEnum;
import com.jackluan.bigflag.common.enums.sign.SignStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/28 0:04
 */
@Data
@NoArgsConstructor
public class SignShareResponseDto extends BaseDto {

    private Long id;

    private Long userId;

    private String headPath;

    private Long flagId;

    private String description;

    private Long fileGroupId;

    private List<String> fileUrlList;

    private Integer threshold;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstant.TIMEZONE)
    private Date achieveDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstant.TIMEZONE)
    private Date deadline;

    private SignStatusEnum status;

    private Integer sequenceNo;

    private List<SignApproverShareResponseDto> approverList;

    private SignApproverResultEnum resultType;

    private QueryTypeEnum queryType;
}
