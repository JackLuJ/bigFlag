package com.jackluan.bigflag.domain.file.logic;

import com.jackluan.bigflag.domain.file.component.dao.IFileDao;
import com.jackluan.bigflag.domain.file.component.dataobject.FileDo;
import com.jackluan.bigflag.domain.file.component.dataobject.extra.FileExtraDo;
import com.jackluan.bigflag.domain.file.convert.FileConvert;
import com.jackluan.bigflag.domain.file.dto.request.FileGroupRequestDto;
import com.jackluan.bigflag.domain.file.dto.request.FileRequestDto;
import com.jackluan.bigflag.domain.file.dto.response.FileResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/16 23:47
 */
@Component
public class FileLogic {

    @Autowired
    private IFileDao fileDao;

    public int countExtra(FileGroupRequestDto fileGroupRequestDto) {
        FileExtraDo fileExtraDo = FileConvert.INSTANCE.convert(fileGroupRequestDto);
        return fileDao.countExtra(fileExtraDo);
    }

    public int updateExtra(FileGroupRequestDto fileGroupRequestDto) {
        FileExtraDo fileExtraDo = FileConvert.INSTANCE.convert(fileGroupRequestDto);
        return fileDao.updateExtra(fileExtraDo);
    }

    public long createFile(FileRequestDto fileRequestDto) {
        FileDo fileDo = FileConvert.INSTANCE.convert(fileRequestDto);
        int count = fileDao.insert(fileDo);
        if (count < 1) {
            return count;
        }
        return fileDo.getId();
    }

    public List<FileResponseDto> queryList(FileRequestDto fileRequestDto) {
        FileDo fileDo = FileConvert.INSTANCE.convert(fileRequestDto);
        List<FileDo> resultList = fileDao.select(fileDo);
        return FileConvert.INSTANCE.convert(resultList);
    }

    public int delete(FileRequestDto fileRequestDto) {
        FileDo fileDo = FileConvert.INSTANCE.convert(fileRequestDto);
        return fileDao.delete(fileDo);
    }
}
