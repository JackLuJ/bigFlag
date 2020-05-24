package com.jackluan.bigflag.provider.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.domain.flag.dto.request.CreateSingInfoRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.provider.dto.request.*;
import com.jackluan.bigflag.provider.dto.response.FlagShareResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/3/4 0:39
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface FlagShareConvert {

    FlagShareConvert INSTANCE = Mappers.getMapper(FlagShareConvert.class);

    Page<FlagRequestDto> convertToDomainDto(Page<FlagShareRequestDto> flagShareRequestDto);

    Page<FlagRequestDto> convertToDomainDto2(Page<FlagListShareRequestDto> flagListShareRequestDtoPage);

    FlagRequestDto convertToDomainDto(FlagCreateShareRequestDto flagCreateShareRequestDto);

    FlagRequestDto convertToDomainDto(ApproverCreateShareRequestDto approverCreateShareRequestDto);

    CreateSingInfoRequestDto convertToDomainDto(CreateSignShareRequestDto createSignShareRequestDto);

    FlagRequestDto convertToDomainDto(FlagUpdateRequestDto flagUpdateRequestDto);

    Page<FlagShareResponseDto> convertToShareDto(Page<FlagResponseDto> flagResponseDtoPage);

    FlagShareResponseDto convertToShareDto(FlagResponseDto flagResponseDto);

    @Mappings({
            @Mapping(target = "id", source = "flagId")
    })
    FlagRequestDto convertToDomainDto(FlagShareRequestDto flagShareRequestDto);

}
