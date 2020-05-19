package com.jackluan.bigflag.provider.service;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.*;
import com.jackluan.bigflag.provider.dto.response.ApproverShareResponseDto;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:14
 */
public interface IApproveService {

    ResultBase<Void> createApprover(ApproverCreateShareRequestDto approverCreateShareRequestDto);

    ResultBase<Void> confirmApprover(ConfirmApproverShareRequestDto confirmApproverShareRequestDto);

    ResultBase<Page<ApproverShareResponseDto>> queryList(Page<QueryApproverShareRequestDto> queryApproverShareRequestDto);

    ResultBase<Void> operate(ApproverOperateShareRequestDto approverOperateShareRequestDto);

    ResultBase<Boolean> needApprove(QueryApproverShareRequestDto queryApproverShareRequestDto);

    ResultBase<ApproverShareResponseDto> queryStatus(QueryApproverStatusShareRequestDto requestDto);

}
