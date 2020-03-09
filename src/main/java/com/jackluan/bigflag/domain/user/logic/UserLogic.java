package com.jackluan.bigflag.domain.user.logic;

import com.jackluan.bigflag.domain.user.component.dao.IUserDao;
import com.jackluan.bigflag.domain.user.component.dataobject.UserDo;
import com.jackluan.bigflag.domain.user.convert.UserConvert;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack.luan
 * @Date: 2020/3/3 23:25
 */
@Component
public class UserLogic {

    @Autowired
    private IUserDao userDao;

    public long createUser(UserRequestDto userRequestDto){
        UserDo userDo = UserConvert.INSTANCE.convertToDo(userRequestDto);
        return userDao.insert(userDo);
    }

}
