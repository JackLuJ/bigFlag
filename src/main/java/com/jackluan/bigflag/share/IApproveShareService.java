package com.jackluan.bigflag.share;

import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.provider.dto.request.*;
import com.jackluan.bigflag.provider.dto.response.ApproverShareResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: jeffery.luan
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

    /**
     * 查询审批人列表
     * @param queryApproverShareRequestDto
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    ResultBase<Page<ApproverShareResponseDto>> queryList(@RequestBody Page<QueryApproverShareRequestDto> queryApproverShareRequestDto);

    /**
     * 操作审批人
     * @param approverOperateShareRequestDto
     * @return
     */
    @RequestMapping(value = "/operate", method = RequestMethod.POST)
    ResultBase<Void> operate(@RequestBody ApproverOperateShareRequestDto approverOperateShareRequestDto);

    /**
     * 查询是否有需要审批的审批者
     * @param queryApproverShareRequestDto
     * @return
     */
    @RequestMapping(value = "/needApprove", method = RequestMethod.POST)
    ResultBase<Boolean> needApprove(@RequestBody QueryApproverShareRequestDto queryApproverShareRequestDto);

    /**
     * 查询审批人状态
     * @param requestDto
     * @return
     */
    @RequestMapping(value = "/query/status", method = RequestMethod.POST)
    ResultBase<ApproverShareResponseDto> queryStatus(@RequestBody QueryApproverStatusShareRequestDto requestDto);
}
