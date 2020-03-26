package com.jackluan.bigflag.common.utils;

import com.jackluan.bigflag.common.base.BaseDto;
import com.jackluan.bigflag.common.base.Page;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;

/**
 * @Author: jack.luan
 * @Date: 2020/3/11 23:43
 */
public class CommonUtils {

    public static String toMD5(String plainText) {
        String result = "";
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte[] b = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T getBean(Class<T> clazz, HttpServletRequest request) {
        //通过该方法获得的applicationContext 已经是初始化之后的applicationContext 容器
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return applicationContext.getBean(clazz);
    }
}
