package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.enums.base.DirectoryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Base64;

/**
 * @Author: jack.luan
 * @Date: 2020/3/21 21:02
 */
@Data
@NoArgsConstructor
public class OperateFileShareRequestDto {

    private Long userId;

    private String base64;

    private String suffix;

    private String fileName;

    private DirectoryEnum type;

    public byte[] getBytes() {
        return StringUtils.isEmpty(this.base64) ? null : Base64.getDecoder().decode(this.base64);
    }

}
