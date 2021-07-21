package com.jinho.aspect;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
public class ControllerLoggingAspect {

    @Pointcut("within(com.jinho..*Controller)")
    public void onRequest() {
    }

    @Around("onRequest()")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("REQUEST \"{}\" [{}] takes {}ms", httpServletRequest.getServletPath(), joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        }
    }

}
