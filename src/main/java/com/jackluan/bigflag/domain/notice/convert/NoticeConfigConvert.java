package com.jackluan.bigflag.domain.notice.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.notice.component.dataobject.NoticeConfigDo;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:27
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface NoticeConfigConvert {

    NoticeConfigConvert INSTANCE = Mappers.getMapper(NoticeConfigConvert.class);

    NoticeConfigDo convertToDo(NoticeConfigRequestDto noticeConfigBaseDto);

    List<NoticeConfigResponseDto> convertDtoList(List<NoticeConfigDo> userDos);

}
