package com.jackluan.bigflag.share.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/3/4 0:39
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface UserShareConvert {

    UserShareConvert INSTANCE = Mappers.getMapper(UserShareConvert.class);

    UserRequestDto convertToDomainDto(UserShareRequestDto userShareRequestDto);
}
