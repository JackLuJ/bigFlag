package com.jackluan.bigflag.domain.sign.logic;

import com.jackluan.bigflag.domain.sign.component.dao.ISignDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignDo;
import com.jackluan.bigflag.domain.sign.convert.SignConvert;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/14 17:23
 */
@Component
public class SignLogic {

    @Autowired
    private ISignDao signDao;

    public long createSign(SignRequestDto signRequestDto){
        SignDo signDo = SignConvert.INSTANCE.convert(signRequestDto);
        int count = signDao.insert(signDo);
        if (count < 1){
            return count;
        }
        return signDo.getId();
    }
}
