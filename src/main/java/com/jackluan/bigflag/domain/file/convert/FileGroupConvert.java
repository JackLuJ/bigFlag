package com.jackluan.bigflag.domain.file.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.file.component.dataobject.FileGroupDo;
import com.jackluan.bigflag.domain.file.component.dataobject.extra.FileExtraDo;
import com.jackluan.bigflag.domain.file.component.dataobject.extra.FileGroupExtraDo;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/17 11:11
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface FileGroupConvert {

    FileGroupConvert INSTANCE = Mappers.getMapper(FileGroupConvert.class);

    FileGroupDo convert(FileGroupRequestDto fileRequestDto);

    List<FileGroupResponseDto> convert(List<FileGroupExtraDo> fileGroupExtraDos);

}
