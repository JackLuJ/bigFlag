package com.jackluan.bigflag.domain.user.logic;

import com.jackluan.bigflag.domain.user.component.dao.IUserDao;
import com.jackluan.bigflag.domain.user.component.dataobject.UserDo;
import com.jackluan.bigflag.domain.user.convert.UserConvert;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: jeffery.luan
 * @Date: 2020/3/3 23:25
 */
@Component
public class UserLogic {

    @Autowired
    private IUserDao userDao;

    public long createUser(UserRequestDto userRequestDto){
        UserDo userDo = UserConvert.INSTANCE.convertToDo(userRequestDto);
        long count = userDao.insert(userDo);
        if (count < 1){
            return count;
        }
        return userDo.getId();
    }

    public List<UserResponseDto> queryList(UserRequestDto userRequestDto){
        UserDo userDo = UserConvert.INSTANCE.convertToDo(userRequestDto);
        List<UserDo> userList = userDao.select(userDo);
        return UserConvert.INSTANCE.convertDtoList(userList);
    }

    public int updateUser(UserRequestDto userRequestDto){
        UserDo userDo = UserConvert.INSTANCE.convertToDo(userRequestDto);
        return userDao.update(userDo);
    }

    public int updateByUnionId(UserRequestDto userRequestDto){
        UserDo userDo = UserConvert.INSTANCE.convertToDo(userRequestDto);
        return userDao.updateByUnionId(userDo);
    }

}
