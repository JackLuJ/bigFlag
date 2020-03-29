package com.jackluan.bigflag.share.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.domain.flag.dto.request.CreateSingInfoRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.FlagResponseDto;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagShareRequestDto;
import com.jackluan.bigflag.share.dto.response.FlagShareResponseDto;
import com.jackluan.bigflag.share.dto.response.UserInfoShareResponseDto;
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

    FlagRequestDto convertToDomainDto(FlagCreateShareRequestDto flagCreateShareRequestDto);

    FlagRequestDto convertToDomainDto(ApproverCreateShareRequestDto approverCreateShareRequestDto);

    CreateSingInfoRequestDto convertToDomainDto(CreateSignShareRequestDto createSignShareRequestDto);

    Page<FlagShareResponseDto> convertToShareDto(Page<FlagResponseDto> flagResponseDtoPage);

    FlagShareResponseDto convertToShareDto(FlagResponseDto flagResponseDto);

    @Mappings({
            @Mapping(target = "id", source = "flagId")
    })
    FlagRequestDto convertToDomainDto(FlagShareRequestDto flagShareRequestDto);

}
