package com.jackluan.bigflag.common.utils;

import com.jackluan.bigflag.common.base.UserInfoBaseDto;
import org.springframework.core.NamedInheritableThreadLocal;

/**
 * @Author: jack.luan
 * @Date: 2020/3/23 0:12
 */
public class UserUtils {

    private static ThreadLocal<UserInfoBaseDto> USER_INFO = new NamedInheritableThreadLocal<>("userInfo");

    public static void setUserInfo(UserInfoBaseDto userInfo){
        USER_INFO.set(userInfo);
    }

    public static UserInfoBaseDto getUser(){
        return USER_INFO.get();
    }

    public static Long getUserId(){
        return USER_INFO.get().getUserId();
    }

    public static void clean(){
        USER_INFO.remove();
    }
}
