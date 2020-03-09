package com.jackluan.bigflag.domain.flag.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagDo;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.user.component.dataobject.UserDo;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:27
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface FlagConvert {

    FlagConvert INSTANCE = Mappers.getMapper(FlagConvert.class);

    FlagDo convertToDo(FlagRequestDto flagRequestDto);

    List<FlagRequestDto> convertDtoList(List<FlagDo> flagDos);

}
