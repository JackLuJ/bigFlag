package com.jackluan.bigflag.domain.flag.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import org.mapstruct.Mapper;
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

    List<ApproverRequestDto> convertDtoList(List<ApproverDo> ApproverDos);

}
