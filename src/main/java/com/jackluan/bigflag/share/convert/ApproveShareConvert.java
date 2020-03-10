package com.jackluan.bigflag.share.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.share.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.share.dto.request.UserShareRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/3/4 0:39
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface ApproveShareConvert {

    ApproveShareConvert INSTANCE = Mappers.getMapper(ApproveShareConvert.class);

    @Mappings({
            @Mapping(target = "score", expression = "java(userShareRequestDto.getApproverType() == null ? null : userShareRequestDto.getApproverType().getScore())")
    })
    ApproverRequestDto convertToDomainDto(ApproverCreateShareRequestDto userShareRequestDto);
}
