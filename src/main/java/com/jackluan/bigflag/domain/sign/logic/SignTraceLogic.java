package com.jackluan.bigflag.domain.sign.logic;

import com.jackluan.bigflag.domain.sign.component.dao.ISignTraceDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignTraceDo;
import com.jackluan.bigflag.domain.sign.convert.SignTraceConvert;
import com.jackluan.bigflag.domain.sign.dto.request.SignTraceRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:24
 */
@Component
public class SignTraceLogic {

    @Autowired
    private ISignTraceDao signTraceDao;

    public long createSignTrace(SignTraceRequestDto signTraceRequestDto){
        SignTraceDo signTraceDo = SignTraceConvert.INSTANCE.convert(signTraceRequestDto);
        int count = signTraceDao.insert(signTraceDo);
        if (count < 1){
            return count;
        }
        return signTraceDo.getId();
    }
}
