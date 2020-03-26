package com.jackluan.bigflag.domain.flag.component.dataobject.extra;

import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import com.jackluan.bigflag.domain.flag.component.dataobject.FlagDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/23 21:42
 */
@Data
@NoArgsConstructor
public class FlagExtraDo extends FlagDo {

    private List<ApproverDo> approverList;

}
