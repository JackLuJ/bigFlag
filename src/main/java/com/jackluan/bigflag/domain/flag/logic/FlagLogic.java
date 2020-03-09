package com.jackluan.bigflag.domain.flag.logic;

import com.jackluan.bigflag.domain.flag.component.dao.IFlagDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagDo;
import com.jackluan.bigflag.domain.flag.convert.FlagConvert;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/8 17:25
 */
@Component
public class FlagLogic {

    @Autowired
    private IFlagDao flagDao;

    public long createFlag(FlagRequestDto flagRequestDto) {
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        return flagDao.insert(flagDo);
    }
}
