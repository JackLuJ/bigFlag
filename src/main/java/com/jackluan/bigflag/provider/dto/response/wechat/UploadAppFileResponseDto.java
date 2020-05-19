package com.jackluan.bigflag.provider.dto.response.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/4/19 13:28
 */
@Data
@NoArgsConstructor
public class UploadAppFileResponseDto {
    private String type;

    private String media_id;

    private Long created_at;

    private List<String> item;
}
