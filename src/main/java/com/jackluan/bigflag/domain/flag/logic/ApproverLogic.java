package com.jackluan.bigflag.domain.flag.logic;

import com.jackluan.bigflag.domain.flag.component.dao.IApproverDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import com.jackluan.bigflag.domain.flag.convert.ApproverConvert;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 22:19
 */
@Component
public class ApproverLogic {

    @Autowired
    private IApproverDao approverDao;

    public long createApprover(ApproverRequestDto approverRequestDto) {
        ApproverDo approverDo = ApproverConvert.INSTANCE.convertToDo(approverRequestDto);
        long count = approverDao.insert(approverDo);
        if (count < 1) {
            return count;
        }
        return approverDo.getId();
    }

    public int updateApprover(ApproverRequestDto approverRequestDto) {
        ApproverDo approverDo = ApproverConvert.INSTANCE.convertToDo(approverRequestDto);
        return approverDao.update(approverDo);
    }

    public List<ApproverResponseDto> queryApproverList(ApproverRequestDto approverRequestDto){
        ApproverDo approverDo = ApproverConvert.INSTANCE.convertToDo(approverRequestDto);
        List<ApproverDo> resultList = approverDao.select(approverDo);
        return ApproverConvert.INSTANCE.convertDtoList(resultList);
    }

    public int queryApproverCount(ApproverRequestDto approverRequestDto) {
        ApproverDo approverDo = ApproverConvert.INSTANCE.convertToDo(approverRequestDto);
        return approverDao.count(approverDo);
    }
}
