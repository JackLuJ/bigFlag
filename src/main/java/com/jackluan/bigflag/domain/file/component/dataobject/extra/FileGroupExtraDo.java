package com.jackluan.bigflag.domain.file.component.dataobject.extra;

import com.jackluan.bigflag.domain.file.component.dataobject.FileDo;
import com.jackluan.bigflag.domain.file.component.dataobject.FileGroupDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/23 22:51
 */
@Data
@NoArgsConstructor
public class FileGroupExtraDo extends FileGroupDo {

    private List<FileDo> fileList;

}
