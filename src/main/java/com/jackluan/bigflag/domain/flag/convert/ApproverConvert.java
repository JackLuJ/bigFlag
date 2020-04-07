package com.jackluan.bigflag.domain.flag.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import com.jackluan.bigflag.domain.flag.component.dataobject.extra.ApproverExtraDo;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.request.CreateSingInfoRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
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
public interface ApproverConvert {

    ApproverConvert INSTANCE = Mappers.getMapper(ApproverConvert.class);

    ApproverDo convertToDo(ApproverRequestDto ApproverRequestDto);

    List<ApproverResponseDto> convertDtoList(List<ApproverDo> ApproverDos);

    ApproverExtraDo convert(ApproverRequestDto ApproverRequestDto);
}
