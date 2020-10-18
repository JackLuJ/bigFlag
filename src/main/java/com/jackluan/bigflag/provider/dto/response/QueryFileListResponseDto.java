package com.jackluan.bigflag.provider.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/7 17:45
 */
@Data
@NoArgsConstructor
public class QueryFileListResponseDto {

    private List<String> urls;

}
