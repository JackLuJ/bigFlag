package com.jackluan.bigflag.domain.user.logic;

import com.jackluan.bigflag.domain.user.component.dao.IUserOpinionDao;
import com.jackluan.bigflag.domain.user.component.dataobject.UserOpinionDo;
import com.jackluan.bigflag.domain.user.convert.UserOpinionConvert;
import com.jackluan.bigflag.domain.user.dto.request.UserOpinionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/4/6 21:12
 */
@Component
public class UserOpinionLogic {

    @Autowired
    private IUserOpinionDao userOpinionDao;

    public long create(UserOpinionRequestDto requestDto) {
        UserOpinionDo userOpinionDo = UserOpinionConvert.INSTANCE.convertToDo(requestDto);
        int count = userOpinionDao.insert(userOpinionDo);
        if (count < 1) {
            return count;
        }
        return userOpinionDo.getId();
    }

}
