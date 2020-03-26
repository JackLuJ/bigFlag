package com.jackluan.bigflag.domain.file.dto.base;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.enums.base.DirectoryEnum;
import com.jackluan.bigflag.common.enums.file.StoreTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 18:57
 */
@Data
@NoArgsConstructor
public class FileBaseDto extends BaseDto {

    private Long id;

    private Long fileGroupId;

    private StoreTypeEnum storeType;

    private DirectoryEnum fileType;

    private String fileName;

    private String suffix;

    private String fileUniqueCode;
}
