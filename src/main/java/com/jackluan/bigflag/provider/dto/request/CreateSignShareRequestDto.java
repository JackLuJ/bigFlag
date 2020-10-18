package com.jackluan.bigflag.provider.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/14 17:40
 */
@Data
@NoArgsConstructor
public class CreateSignShareRequestDto {

    private Long userId;

    private Long flagId;

    private String description;

    private List<String> fileUniqueCodes;

}
