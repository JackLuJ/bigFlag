package com.jackluan.bigflag.domain.flag.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.flag.component.dataobject.AchieveConfigDo;
import com.jackluan.bigflag.domain.flag.dto.request.AchieveConfigRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/3 23:27
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface AchieveConfigConvert {

    AchieveConfigConvert INSTANCE = Mappers.getMapper(AchieveConfigConvert.class);

    AchieveConfigDo convertToDo(AchieveConfigRequestDto AchieveConfigRequestDto);

    List<AchieveConfigRequestDto> convertDtoList(List<AchieveConfigDo> AchieveConfigDos);

}
