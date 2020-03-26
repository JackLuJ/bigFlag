package com.jackluan.bigflag.domain.sign.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignApproverDo;
import com.jackluan.bigflag.domain.sign.dto.request.SignApproverRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:25
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface SignApproverConvert {

    SignApproverConvert INSTANCE = Mappers.getMapper(SignApproverConvert.class);

    SignApproverDo convert(SignApproverRequestDto signApproverRequestDto);

}
