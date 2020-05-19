package com.jackluan.bigflag.provider.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.provider.dto.request.FlagCreateShareRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jack.luan
 * @Date: 2020/3/4 0:39
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface NoticeShareConvert {

    NoticeShareConvert INSTANCE = Mappers.getMapper(NoticeShareConvert.class);

    NoticeConfigRequestDto convertToNoticeConfig(FlagCreateShareRequestDto flagCreateShareRequestDto);
}
