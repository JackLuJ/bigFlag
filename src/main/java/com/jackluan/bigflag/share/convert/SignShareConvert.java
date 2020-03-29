package com.jackluan.bigflag.share.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.common.base.Page;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.ApproverResponseDto;
import com.jackluan.bigflag.domain.flag.dto.response.CreateSingInfoResponseDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignApproverResponseDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import com.jackluan.bigflag.share.dto.request.CreateSignShareRequestDto;
import com.jackluan.bigflag.share.dto.request.SignShareRequestDto;
import com.jackluan.bigflag.share.dto.response.SignShareResponseDto;
import com.jackluan.bigflag.share.dto.response.UserInfoShareResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/18 10:27
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface SignShareConvert {

    SignShareConvert INSTANCE = Mappers.getMapper(SignShareConvert.class);

    SignRequestDto convertToDomainDto(CreateSignShareRequestDto createSignShareRequestDto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "approverId", source = "id"),
            @Mapping(target = "approverUserId", source = "userId"),
    })
    SignApproverRequestDto convertToDomainDto(ApproverResponseDto approverResponseDto);

    List<SignApproverRequestDto> convertToDomainDto(List<ApproverResponseDto> approverResponses);

    Page<SignRequestDto> convertToDomainDto(Page<SignShareRequestDto> signShareRequestDtoPage);

    Page<SignShareResponseDto> convertToShareDto(Page<SignResponseDto> signResponseDtoPage);

    @Mappings({
            @Mapping(target = "approverList", source = "signApproverList")
    })
    SignShareResponseDto convertToShareDto(SignResponseDto signResponseDto);

    @Mappings({
            @Mapping(target = "userId", source = "approverUserId")
    })
    UserInfoShareResponseDto convertToShareDto(SignApproverResponseDto signApproverResponseDto);

}
