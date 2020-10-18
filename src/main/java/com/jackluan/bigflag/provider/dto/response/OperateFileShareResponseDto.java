package com.jackluan.bigflag.provider.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/21 21:01
 */
@Data
@NoArgsConstructor
public class OperateFileShareResponseDto implements Serializable {

    private String fileUniqueCode;

}
