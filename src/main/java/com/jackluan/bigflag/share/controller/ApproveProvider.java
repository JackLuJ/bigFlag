package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.IApproveShareService;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.ApproverOperateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.ConfirmApproverShareRequestDto;
import com.jackluan.bigflag.share.dto.request.QueryApproverShareRequestDto;
import com.jackluan.bigflag.share.dto.response.ApproverShareResponseDto;
import com.jackluan.bigflag.share.service.IApproveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        log.info("into createApprover method. param is {}",converter.objToJson(approverCreateShareRequestDto));
        return approveService.createApprover(approverCreateShareRequestDto);
    }

    @Override
    public ResultBase<Void> confirmApprover(ConfirmApproverShareRequestDto confirmApproverShareRequestDto) {
        log.info("into confirmApprover method. param is {}",converter.objToJson(confirmApproverShareRequestDto));
        return approveService.confirmApprover(confirmApproverShareRequestDto);
    }

    @Override
    public ResultBase<Page<ApproverShareResponseDto>> queryList(Page<QueryApproverShareRequestDto> queryApproverShareRequestDto) {
        log.info("into queryList method. param is {}",converter.objToJson(queryApproverShareRequestDto));
        return approveService.queryList(queryApproverShareRequestDto);
    }

    @Override
    public ResultBase<Void> operate(ApproverOperateShareRequestDto approverOperateShareRequestDto) {
        log.info("into operate method. param is {}",converter.objToJson(approverOperateShareRequestDto));
        return approveService.operate(approverOperateShareRequestDto);
    }

    @Override
    public ResultBase<Boolean> needApprove(QueryApproverShareRequestDto queryApproverShareRequestDto) {
        log.info("into needApprove method. param is {}",converter.objToJson(queryApproverShareRequestDto));
        return approveService.needApprove(queryApproverShareRequestDto);
    }
}
