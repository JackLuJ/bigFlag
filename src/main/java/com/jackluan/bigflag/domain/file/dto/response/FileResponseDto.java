package com.jackluan.bigflag.domain.file.dto.response;

import com.jackluan.bigflag.domain.file.dto.base.FileBaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/16 23:45
 */
@Data
@NoArgsConstructor
public class FileResponseDto extends FileBaseDto {

    private String url;

}
