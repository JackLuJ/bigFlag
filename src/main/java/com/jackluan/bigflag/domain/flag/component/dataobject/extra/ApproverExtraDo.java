package com.jackluan.bigflag.domain.flag.component.dataobject.extra;

import com.jackluan.bigflag.domain.flag.component.dataobject.ApproverDo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/4/4 18:50
 */
@Data
@NoArgsConstructor
public class ApproverExtraDo extends ApproverDo {

    private List<Integer> statusList;

}
