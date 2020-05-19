package com.jackluan.bigflag.provider.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.utils.ValidationUtils;
import com.jackluan.bigflag.share.IApproveShareService;
import com.jackluan.bigflag.provider.dto.request.*;
import com.jackluan.bigflag.provider.dto.response.ApproverShareResponseDto;
import com.jackluan.bigflag.provider.service.IApproveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:13
 */
@Slf4j
@CheckToken
@RestController
public class ApproveProvider implements IApproveShareService {
    private JsonConverter converter = new JsonConverter();

    @Autowired
    private IApproveService approveService;

    @Override
    public ResultBase<Void> createApprover(ApproverCreateShareRequestDto approverCreateShareRequestDto) {
        return approveService.createApprover(approverCreateShareRequestDto);
    }

    @Override
    public ResultBase<Void> confirmApprover(ConfirmApproverShareRequestDto confirmApproverShareRequestDto) {
        return approveService.confirmApprover(confirmApproverShareRequestDto);
    }

    @Override
    public ResultBase<Page<ApproverShareResponseDto>> queryList(Page<QueryApproverShareRequestDto> queryApproverShareRequestDto) {
        return approveService.queryList(queryApproverShareRequestDto);
    }

    @Override
    public ResultBase<Void> operate(ApproverOperateShareRequestDto approverOperateShareRequestDto) {
        return approveService.operate(approverOperateShareRequestDto);
    }

    @Override
    public ResultBase<Boolean> needApprove(QueryApproverShareRequestDto queryApproverShareRequestDto) {
        return approveService.needApprove(queryApproverShareRequestDto);
    }

    @Override
    public ResultBase<ApproverShareResponseDto> queryStatus(QueryApproverStatusShareRequestDto requestDto) {
        ValidationUtils.isEmpty(requestDto);
        return approveService.queryStatus(requestDto);
    }
}
