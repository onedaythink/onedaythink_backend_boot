package com.spring.onedaythink.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
@Log4j2
public class TransactionAspect {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Pointcut("execution(public * com..service.*.*(..))")
    public void serviceMethods() {

    }

    @Around("serviceMethods()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(status);
//            log.debug("======> logAspect root: "+ joinPoint.getSignature().getDeclaringTypeName());
//            log.debug("======> logAspect Method : " + joinPoint.getSignature().getName());
            return result;
        } catch (Exception e) {
            transactionManager.rollback(status);
//            log.debug("======> logAspect root: " + joinPoint.getSignature().getDeclaringTypeName());
//            log.debug("======> logAspect Method : " + joinPoint.getSignature().getName());
            throw e;
        }
    }

}
