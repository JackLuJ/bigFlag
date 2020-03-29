package com.jackluan.bigflag.domain.file.logic;

import com.jackluan.bigflag.domain.file.component.dao.IFileGroupDao;
import com.jackluan.bigflag.domain.file.component.dataobject.FileGroupDo;
import com.jackluan.bigflag.domain.file.component.dataobject.extra.FileGroupExtraDo;
import com.jackluan.bigflag.domain.file.convert.FileGroupConvert;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileGroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/16 23:48
 */
@Component
public class FileGroupLogic {

    @Autowired
    private IFileGroupDao fileGroupDao;

    public long createFileGroup(FileGroupRequestDto fileGroupRequestDto){
        FileGroupDo fileGroupDo = FileGroupConvert.INSTANCE.convert(fileGroupRequestDto);
        int count = fileGroupDao.insert(fileGroupDo);
        if (count < 1){
            return count;
        }
        return fileGroupDo.getId();
    }

    public List<FileGroupResponseDto> queryGroup(FileGroupRequestDto fileGroupRequestDto){
        FileGroupDo fileGroupDo = FileGroupConvert.INSTANCE.convert(fileGroupRequestDto);
        List<FileGroupExtraDo> resultList = fileGroupDao.selectWithFile(fileGroupDo);
        return FileGroupConvert.INSTANCE.convert(resultList);
    }

    public int delete(FileGroupRequestDto fileGroupRequestDto){
        FileGroupDo fileGroupDo = FileGroupConvert.INSTANCE.convert(fileGroupRequestDto);
        return fileGroupDao.delete(fileGroupDo);
    }

}
