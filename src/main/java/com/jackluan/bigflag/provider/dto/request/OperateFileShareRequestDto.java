package com.jackluan.bigflag.provider.dto.request;

import com.jackluan.bigflag.common.enums.base.DirectoryEnum;
import com.jackluan.bigflag.common.utils.HttpUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Base64;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/21 21:02
 */
@Data
@NoArgsConstructor
public class OperateFileShareRequestDto {

    private Long userId;

    private String base64;

    private String suffix;

    private String fileName;

    private String fileURL;

    private DirectoryEnum type;

    public byte[] getBytes() {
        byte[] bytes = null;
        if (!StringUtils.isEmpty(fileURL)){
            bytes = HttpUtils.getFile(fileURL);
        }else if (!StringUtils.isEmpty(base64)){
            bytes = Base64.getDecoder().decode(this.base64);
        }
        return bytes;
    }

}
