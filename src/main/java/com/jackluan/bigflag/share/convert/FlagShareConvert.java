package com.jackluan.bigflag.share.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.domain.flag.dto.request.FlagRequestDto;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.FlagShareRequestDto;
import org.mapstruct.Mapper;
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
}
