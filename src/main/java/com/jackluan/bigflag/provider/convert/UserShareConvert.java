package com.jackluan.bigflag.provider.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.user.dto.request.UserOpinionRequestDto;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.provider.dto.request.UserOpinionShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.UserShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.wechat.Code2SessionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/3/4 0:39
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface UserShareConvert {

    UserShareConvert INSTANCE = Mappers.getMapper(UserShareConvert.class);

    UserRequestDto convertToDomainDto(UserShareRequestDto userShareRequestDto);

    @Mappings({
            @Mapping(target = "appOpenId", source = "openid"),
            @Mapping(target = "unionId", source = "unionid"),
            @Mapping(target = "sessionKey", source = "session_key")
    })
    UserRequestDto convertToDomainDto(Code2SessionResponseDto responseDto);

    UserOpinionRequestDto convertToDomainDto(UserOpinionShareRequestDto opinionShareRequestDto);
}
