package com.jackluan.bigflag.share.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: jack.luan
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
