package com.jackluan.bigflag.domain.sign.logic;

import com.jackluan.bigflag.domain.sign.component.dao.ISignDao;
import com.jackluan.bigflag.domain.sign.component.dataobject.SignDo;
import com.jackluan.bigflag.domain.sign.component.dataobject.extra.SignExtraDo;
import com.jackluan.bigflag.domain.sign.convert.SignConvert;
import com.jackluan.bigflag.domain.sign.dto.request.SignRequestDto;
import com.jackluan.bigflag.domain.sign.dto.response.SignResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jeffery.luan
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

    public int count(SignRequestDto signRequestDto){
        SignDo signDo = SignConvert.INSTANCE.convert(signRequestDto);
        int count = signDao.count(signDo);
        return count;
    }

    public int selectApproveSignCount(SignRequestDto signRequestDto){
        SignDo signDo = SignConvert.INSTANCE.convert(signRequestDto);
        int count = signDao.selectApproveSignCount(signDo);
        return count;
    }

    public List<SignResponseDto> querySignList(SignRequestDto signRequestDto){
        SignDo signDo = SignConvert.INSTANCE.convert(signRequestDto);
        List<SignDo> signList = signDao.select(signDo);
        return SignConvert.INSTANCE.convert(signList);
    }

    public List<SignResponseDto> queryApproveSignList(SignRequestDto signRequestDto){
        SignDo signDo = SignConvert.INSTANCE.convert(signRequestDto);
        List<SignExtraDo> signList = signDao.selectApproveSignList(signDo);
        return SignConvert.INSTANCE.convertExtra(signList);
    }

    public int selectSignCountWithDate(SignRequestDto signRequestDto){
        SignExtraDo signExtraDo = SignConvert.INSTANCE.convertExtra(signRequestDto);
        return signDao.selectSignCountWithDate(signExtraDo);
    }

    public int update(SignRequestDto signRequestDto){
        SignDo signDo = SignConvert.INSTANCE.convert(signRequestDto);
        return signDao.update(signDo);
    }

    public List<SignResponseDto> selectSignListWithDate(SignRequestDto signRequestDto){
        SignExtraDo signExtraDo = SignConvert.INSTANCE.convertExtra(signRequestDto);
        List<SignDo> signList = signDao.selectSignListWithDate(signExtraDo);
        return SignConvert.INSTANCE.convert(signList);
    }

    public int delete(SignRequestDto signRequestDto){
        SignDo signDo = SignConvert.INSTANCE.convert(signRequestDto);
        return signDao.delete(signDo);
    }
}
