package com.jackluan.bigflag.provider.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jeffery.luan
 * @Date: 2020/5/30 20:16
 */
@Data
@NoArgsConstructor
public class DeleteSignRequestDto {

    private Long id;

    private Long userId;

}
