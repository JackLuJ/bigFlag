package com.jackluan.bigflag.domain.flag.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagTraceDo;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagTraceRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:27
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface FlagTraceConvert {

    FlagTraceConvert INSTANCE = Mappers.getMapper(FlagTraceConvert.class);

    FlagTraceDo convertToDo(FlagTraceRequestDto flagRequestDto);

    List<FlagTraceRequestDto> convertDtoList(List<FlagTraceDo> flagDos);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "id", target = "flagId")
    })
    FlagTraceRequestDto convertFlag(FlagRequestDto flagRequestDto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "id", target = "flagId")
    })
    FlagTraceRequestDto convertFlag(FlagResponseDto flagResponseDto);

}
