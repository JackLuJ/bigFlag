package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:07
 */
@RequestMapping(value = "/approve")
public interface IApproveShareService {

    @RequestMapping(value = "createApprover", method = RequestMethod.POST)
    ResultBase<Void> createApprover(ApproverCreateShareRequestDto approverCreateShareRequestDto);

}
