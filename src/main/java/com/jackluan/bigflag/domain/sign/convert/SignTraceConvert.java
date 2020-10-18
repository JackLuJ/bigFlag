package com.jackluan.bigflag.domain.sign.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignTraceDo;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignTraceRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:25
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface SignTraceConvert {

    SignTraceConvert INSTANCE = Mappers.getMapper(SignTraceConvert.class);


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "signId", source = "id")
    })
    SignTraceRequestDto convert(SignRequestDto signRequestDto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "signId", source = "id")
    })
    SignTraceRequestDto convert(SignResponseDto signResponseDto);

    SignTraceDo convert(SignTraceRequestDto signTraceRequestDto);

}
