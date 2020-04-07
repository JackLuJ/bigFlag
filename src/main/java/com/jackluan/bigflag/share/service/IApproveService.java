package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.ApproverOperateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.ConfirmApproverShareRequestDto;
import com.jackluan.bigflag.share.dto.request.QueryApproverShareRequestDto;
import com.jackluan.bigflag.share.dto.response.ApproverShareResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

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
}
