package com.jackluan.bigflag.common.aspect;

import com.jackluan.bigflag.common.base.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author: jeffery.luan
 * @Date: 2020/4/25 22:05
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private JsonConverter converter = new JsonConverter();

    @Pointcut("execution(* com.jackluan.bigflag.share..*(..))")
    public void LogAspect() {
    }

    @Before("LogAspect()")
    public void doBefore(JoinPoint joinPoint) {
        String codeMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        log.info("###### code method:" + codeMethod);
        if ("com.jackluan.bigflag.provider.controller.FileProvider.uploadFile".equals(codeMethod)) {
            log.info("###### request param: upload file");
        } else if ("com.jackluan.bigflag.provider.controller.LoginProvider.processMsg".equals(codeMethod) || "com.jackluan.bigflag.provider.controller.LoginProvider.processMsgApp".equals(codeMethod)) {
            log.info("###### request param: wechat msg");
        } else {
            try {
                log.info("###### request param:" + converter.objToJson(joinPoint.getArgs()));
            } catch (Exception e) {
                log.info("###### request param format error!!");
            }
        }
        // 方法本传了哪些参数
    }

    @After("LogAspect()")
    public void doAfter(JoinPoint joinPoint) {
    }

    @AfterReturning(returning = "ret", pointcut = "LogAspect()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        log.info("###### response param:" + converter.objToJson(ret));
    }

    @AfterThrowing("LogAspect()")
    public void deAfterThrowing(JoinPoint joinPoint) {
    }

    @Around("LogAspect()")
    public Object deAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

}