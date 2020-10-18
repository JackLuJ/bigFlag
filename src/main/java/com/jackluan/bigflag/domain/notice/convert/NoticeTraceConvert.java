package com.jackluan.bigflag.domain.notice.convert;

import com.jackluan.bigflag.common.base.KeyValueEnumMapper;
import com.jackluan.bigflag.domain.notice.component.dataobject.NoticeConfigDo;
import com.jackluan.bigflag.domain.notice.component.dataobject.NoticeTraceDo;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeConfigRequestDto;
import com.jackluan.bigflag.domain.notice.dto.request.NoticeTraceRequestDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeConfigResponseDto;
import com.jackluan.bigflag.domain.notice.dto.response.NoticeTraceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/3 23:27
 */
@Mapper(uses = KeyValueEnumMapper.class)
public interface NoticeTraceConvert {

    NoticeTraceConvert INSTANCE = Mappers.getMapper(NoticeTraceConvert.class);

    NoticeTraceDo convertToDo(NoticeTraceRequestDto noticeTraceRequestDto);

    List<NoticeTraceResponseDto> convertDtoList(List<NoticeTraceDo> noticeTraceDos);

}
