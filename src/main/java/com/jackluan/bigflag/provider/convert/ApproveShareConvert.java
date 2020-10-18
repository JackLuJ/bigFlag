package com.jackluan.bigflag.provider.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.domain.flag.dto.request.ApproverRequestDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.provider.dto.request.ApproverCreateShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.ConfirmApproverShareRequestDto;
import com.jackluan.bigflag.provider.dto.request.QueryApproverShareRequestDto;
import com.jackluan.bigflag.provider.dto.response.ApproverShareResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/4 0:39
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface ApproveShareConvert {

    ApproveShareConvert INSTANCE = Mappers.getMapper(ApproveShareConvert.class);

    @Mappings({
            @Mapping(target = "score", expression = "java(userShareRequestDto.getApproverType() == null ? null : userShareRequestDto.getApproverType().getScore())")
    })
    ApproverRequestDto convertToDomainDto(ApproverCreateShareRequestDto userShareRequestDto);

    @Mappings({
            @Mapping(target = "id", source = "approverId"),
            @Mapping(target = "status", expression = "java(userShareRequestDto.getDecision() == com.jackluan.bigflag.common.enums.base.YesNoEnum.YES ? com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum.EFFECTIVE : com.jackluan.bigflag.common.enums.flag.ApproverStatusEnum.REFUSE)")
    })
    ApproverRequestDto convertToDomainDto(ConfirmApproverShareRequestDto userShareRequestDto);

    Page<ApproverRequestDto> convertToDomainDto(Page<QueryApproverShareRequestDto> queryApproverShareRequestDto);

    Page<ApproverShareResponseDto> convertToShareDto(Page<ApproverResponseDto> approverResponseDtoPage);

    @Mappings({
            @Mapping(target = "approverId", source = "id")
    })
    ApproverShareResponseDto convertToShareDto(ApproverResponseDto approverResponseDto);
}
