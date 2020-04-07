package com.jackluan.bigflag.domain.user.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.user.component.dataobject.UserOpinionDo;
import com.jackluan.bigflag.domain.user.dto.request.UserOpinionRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 21:13
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface UserOpinionConvert {

    UserOpinionConvert INSTANCE = Mappers.getMapper(UserOpinionConvert.class);

    UserOpinionDo convertToDo(UserOpinionRequestDto requestDto);

}
