package com.jackluan.bigflag.share.service;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:14
 */
public interface IApproveService {

    ResultBase<Void> createApprover(ApproverCreateShareRequestDto approverCreateShareRequestDto);

}
