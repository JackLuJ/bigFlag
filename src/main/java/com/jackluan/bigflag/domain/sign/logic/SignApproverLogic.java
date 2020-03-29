package com.jackluan.bigflag.domain.sign.logic;

import com.jackluan.bigflag.domain.sign.component.dao.ISignApproverDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignApproverDo;
import com.jackluan.bigflag.domain.sign.convert.SignApproverConvert;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignApproverResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:24
 */
@Component
public class SignApproverLogic {

    @Autowired
    private ISignApproverDao signApproverDao;

    public long createSignApprover(SignApproverRequestDto signApproverRequestDto){
        SignApproverDo signApproverDo = SignApproverConvert.INSTANCE.convert(signApproverRequestDto);
        int count = signApproverDao.insert(signApproverDo);
        if (count < 1){
            return count;
        }
        return signApproverDo.getId();
    }

    public List<SignApproverResponseDto> queryList(SignApproverRequestDto signApproverRequestDto){
        SignApproverDo signApproverDo = SignApproverConvert.INSTANCE.convert(signApproverRequestDto);
        List<SignApproverDo> resultList = signApproverDao.select(signApproverDo);
        return SignApproverConvert.INSTANCE.convert(resultList);
    }

    public List<SignApproverResponseDto> selectByResultTypeNotIn(SignApproverRequestDto signApproverRequestDto){
        SignApproverDo signApproverDo = SignApproverConvert.INSTANCE.convert(signApproverRequestDto);
        List<SignApproverDo> resultList = signApproverDao.selectByResultTypeNotIn(signApproverDo);
        return SignApproverConvert.INSTANCE.convert(resultList);
    }

}
