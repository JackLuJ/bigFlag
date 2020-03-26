package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.ConfirmApproverShareRequestDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:07
 */
@RequestMapping(value = "/approve")
public interface IApproveShareService {

    /**
     * 创建审批人
     *
     * @param approverCreateShareRequestDto
     * @return
     */
    @RequestMapping(value = "/createApprover", method = RequestMethod.POST)
    ResultBase<Void> createApprover(@RequestBody ApproverCreateShareRequestDto approverCreateShareRequestDto);

    /**
     * 确认审批人
     *
     * @param confirmApproverShareRequestDto
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    ResultBase<Void> confirmApprover(@RequestBody ConfirmApproverShareRequestDto confirmApproverShareRequestDto);
}
