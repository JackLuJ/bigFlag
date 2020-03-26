package com.jackluan.bigflag.domain.file.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.file.component.dataobject.FileDo;
import com.jackluan.bigflag.domain.file.component.dataobject.extra.FileExtraDo;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.request.FileRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/3/17 10:11
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "fileGroupId", source = "id")
    })
    FileExtraDo convert(FileGroupRequestDto fileGroupRequestDto);

    FileDo convert(FileRequestDto fileRequestDto);

}
