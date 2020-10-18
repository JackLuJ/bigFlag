package com.jackluan.bigflag.domain.file.component.dataobject.extra;

import com.jackluan.bigflag.domain.file.component.dataobject.FileDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/17 0:34
 */
@Data
@NoArgsConstructor
public class FileExtraDo extends FileDo {

    List<String> fileUniqueCodeList;

}
