package com.jackluan.bigflag.domain.file.dto.response;

import com.jackluan.bigflag.domain.file.dto.base.FileGroupBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/16 23:45
 */
@Data
@NoArgsConstructor
public class FileGroupResponseDto extends FileGroupBaseDto {

    private List<FileResponseDto> fileList;

}
