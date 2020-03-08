package com.jackluan.bigflag.domain.user.logic;

import com.jackluan.bigflag.domain.user.component.dao.IUserDao;
import com.jackluan.bigflag.domain.user.component.dataobject.UserDo;
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

    public long createUser(UserDo userDo){
        return userDao.insert(userDo);
    }

}
