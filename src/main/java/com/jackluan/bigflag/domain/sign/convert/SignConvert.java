package com.jackluan.bigflag.domain.sign.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignDo;
import com.jackluan.bigflag.domain.sign.component.dataobject.extra.SignExtraDo;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignTraceRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:25
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface SignConvert {

    SignConvert INSTANCE = Mappers.getMapper(SignConvert.class);

    SignDo convert(SignRequestDto signRequestDto);

    List<SignResponseDto> convert(List<SignDo> signDos);

    List<SignResponseDto> convertExtra(List<SignExtraDo> signDos);

    SignExtraDo convertExtra(SignRequestDto signRequestDto);

    @Mappings({
            @Mapping(target = "id", source = "signId")
    })
    SignRequestDto convert(SignTraceRequestDto signTraceRequestDto);
}
