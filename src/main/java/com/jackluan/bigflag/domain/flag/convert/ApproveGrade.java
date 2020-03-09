package com.jackluan.bigflag.domain.flag.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.flag.component.dataobject.ApproveGradeDo;
import com.jackluan.bigflag.domain.flag.dto.request.ApproveGradeRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:27
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface ApproveGrade {

    ApproveGrade INSTANCE = Mappers.getMapper(ApproveGrade.class);

    ApproveGradeDo convertToDo(ApproveGradeRequestDto ApproveGradeRequestDto);

    List<ApproveGradeRequestDto> convertDtoList(List<ApproveGradeDo> ApproveGradeDos);

}
