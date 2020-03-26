package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.ConfirmApproverShareRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:14
 */
public interface IApproveService {

    ResultBase<Void> createApprover(ApproverCreateShareRequestDto approverCreateShareRequestDto);

    ResultBase<Void> confirmApprover(ConfirmApproverShareRequestDto confirmApproverShareRequestDto);

}
