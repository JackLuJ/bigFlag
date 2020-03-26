package com.jackluan.bigflag.common.interceptor;

import com.jackluan.bigflag.common.annotation.CheckToken;
import com.jackluan.bigflag.common.annotation.PassToken;
import com.jackluan.bigflag.common.base.BigFlagRuntimeException;
import com.jackluan.bigflag.common.base.JsonConverter;
import com.jackluan.bigflag.common.base.ResultBase;
import com.jackluan.bigflag.common.base.UserInfoBaseDto;
import com.jackluan.bigflag.common.constant.ResultCodeConstant;
import com.jackluan.bigflag.common.utils.CommonUtils;
import com.jackluan.bigflag.common.utils.UserUtils;
import com.jackluan.bigflag.domain.user.dto.request.UserRequestDto;
import com.jackluan.bigflag.domain.user.dto.response.UserResponseDto;
import com.jackluan.bigflag.domain.user.handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jack.luan
 * @Date: 2020/3/22 14:03
 */
@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        boolean isLogin = false;
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查是否有CheckToken注解，有则进行验证
        if (!method.getDeclaringClass().isAnnotationPresent(CheckToken.class)){
            return true;
        }

        //检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //校验token
        if (!StringUtils.isEmpty(token)) {
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setToken(token);
            UserHandler userHandler = CommonUtils.getBean(UserHandler.class, request);
            ResultBase<List<UserResponseDto>> resultBase = userHandler.queryUser(userRequestDto);
            if (resultBase.isSuccess() && !resultBase.isEmptyValue()) {
                UserResponseDto user = resultBase.getValue().get(0);

                UserInfoBaseDto userInfoBase = new UserInfoBaseDto();
                userInfoBase.setUserId(user.getId());

                UserUtils.setUserInfo(userInfoBase);
                isLogin = true;
            }
        }

        if (!isLogin) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            PrintWriter out = null;
            try {
                Map<String, String> msg = new HashMap<>(4);
                msg.put("code", ResultCodeConstant.NOT_LOGIN);
                msg.put("msg", "please login");
                JsonConverter converter = new JsonConverter();
                out = response.getWriter();
                out.append(converter.objToJson(msg));
            } catch (Exception e) {
                response.setStatus(400);
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return false;
        }


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtils.clean();
    }
}
