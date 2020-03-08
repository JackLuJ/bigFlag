package com.jackluan.bigflag.domain.file.component.dataobject;

import com.jackluan.bigflag.common.base.BaseDo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jack.luan
 * @Date: 2020/3/7 18:57
 */
@Data
@NoArgsConstructor
public class FileDo extends BaseDo {

    private Long id;

    private Integer storeType;

    private String filePath;

    private String fileName;

}
