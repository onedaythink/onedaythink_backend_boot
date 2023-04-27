package com.spring.onedaythink.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private Logger log = LogManager.getLogger("case 3");
    @Around("execution(* com..service.*Service.edit*(..))")
    public Object aopLogging(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();
        log.debug("======> logAspect root: "
        + pjp.getSignature().getDeclaringTypeName());

        log.debug("======> logAspect Method : " +
                pjp.getSignature().getName());
        return result;

    }

}
