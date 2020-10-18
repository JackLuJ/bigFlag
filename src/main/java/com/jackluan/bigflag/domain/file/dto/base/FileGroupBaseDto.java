package com.jackluan.bigflag.domain.file.dto.base;

import com.jackluan.bigflag.common.base.BaseDo;
import com.jackluan.bigflag.common.base.BaseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/7 18:58
 */
@Data
@NoArgsConstructor
public class FileGroupBaseDto extends BaseDto {

    private Long id;

    private Long userId;

}
