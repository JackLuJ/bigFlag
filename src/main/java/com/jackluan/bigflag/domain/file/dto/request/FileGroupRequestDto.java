package com.jackluan.bigflag.domain.file.dto.request;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.domain.file.dto.base.FileGroupBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/16 23:45
 */
@Data
@NoArgsConstructor
public class FileGroupRequestDto extends FileGroupBaseDto {

    List<String> fileUniqueCodeList;

}
