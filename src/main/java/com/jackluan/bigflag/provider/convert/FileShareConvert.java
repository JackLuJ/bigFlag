package com.jackluan.bigflag.provider.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.provider.dto.request.CreateSignShareRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/17 11:39
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface FileShareConvert {

    FileShareConvert INSTANCE = Mappers.getMapper(FileShareConvert.class);

    @Mappings({
            @Mapping(source = "fileUniqueCodes", target = "fileUniqueCodeList")
    })
    FileGroupRequestDto convert(CreateSignShareRequestDto createSignShareRequestDto);
}
