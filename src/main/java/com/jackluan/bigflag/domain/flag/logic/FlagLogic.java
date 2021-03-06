package com.jackluan.bigflag.domain.flag.logic;

import com.jackluan.bigflag.domain.flag.component.dao.IFlagDao;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagDo;
import com.jackluan.bigflag.domain.flag.component.dataobject.extra.FlagExtraDo;
import com.jackluan.bigflag.domain.flag.convert.FlagConvert;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/8 17:25
 */
@Component
public class FlagLogic {

    @Autowired
    private IFlagDao flagDao;

    public long createFlag(FlagRequestDto flagRequestDto) {
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        long count = flagDao.insert(flagDo);
        if (count < 1){
            return count;
        }
        return flagDo.getId();
    }

    public List<FlagResponseDto> queryFlagList(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        List<FlagDo> resultList = flagDao.select(flagDo);
        return FlagConvert.INSTANCE.convertDtoList(resultList);
    }

    public int queryCount(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        return flagDao.count(flagDo);
    }

    public int increasePerformance(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        return flagDao.increasePerformance(flagDo);
    }

    public int updateFlag(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        return flagDao.update(flagDo);
    }

    public int updatePassFlag(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        return flagDao.updatePassFlag(flagDo);
    }

    public int queryApproveFlagCount(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        return flagDao.selectApproveFlagCount(flagDo);
    }

    public List<FlagResponseDto> queryApproveFlagList(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        List<FlagDo> resultList = flagDao.selectApproveFlagList(flagDo);
        return FlagConvert.INSTANCE.convertDtoList(resultList);
    }

    public int updateByDeadline(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        return flagDao.updateByDeadline(flagDo);
    }

    public List<FlagResponseDto> selectByDeadline(FlagRequestDto flagRequestDto){
        FlagDo flagDo = FlagConvert.INSTANCE.convertToDo(flagRequestDto);
        List<FlagDo> resultList = flagDao.selectByDeadline(flagDo);
        return FlagConvert.INSTANCE.convertDtoList(resultList);
    }

    public List<FlagResponseDto> queryFlagListExtra(FlagRequestDto flagRequestDto){
        FlagExtraDo flagDo = FlagConvert.INSTANCE.convertToExtraDo(flagRequestDto);
        List<FlagDo> resultList = flagDao.selectExtra(flagDo);
        return FlagConvert.INSTANCE.convertDtoList(resultList);
    }

    public int queryCountExtra(FlagRequestDto flagRequestDto){
        FlagExtraDo flagDo = FlagConvert.INSTANCE.convertToExtraDo(flagRequestDto);
        return flagDao.countExtra(flagDo);
    }
}
