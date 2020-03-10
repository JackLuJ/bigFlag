package com.jackluan.bigflag.share.controller;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.IApproveShareService;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.service.IApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:13
 */
@RestController
public class ApproveProvider implements IApproveShareService {

    @Autowired
    private IApproveService approveService;

    @Override
    public ResultBase<Void> createApprover(ApproverCreateShareRequestDto approverCreateShareRequestDto) {
        return approveService.createApprover(approverCreateShareRequestDto);
    }
}
