package com.jackluan.bigflag.domain.flag.logic;

import com.jackluan.bigflag.domain.flag.component.dao.IFlagTraceDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagTraceDo;
import com.jackluan.bigflag.domain.flag.convert.FlagTraceConvert;
import com.jackluan.bigflag.domain.flag.dto.request.FlagTraceRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/8 17:25
 */
@Component
public class FlagTraceLogic {

    @Autowired
    private IFlagTraceDao flagTraceDao;

    public long createFlagTrace(FlagTraceRequestDto flagRequestDto) {
        FlagTraceDo flagTraceDoDo = FlagTraceConvert.INSTANCE.convertToDo(flagRequestDto);
        long count = flagTraceDao.insert(flagTraceDoDo);
        if (count < 1){
            return count;
        }
        return flagTraceDoDo.getId();
    }
}
