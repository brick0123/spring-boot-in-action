package com.jinho;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    private static final String REQUEST_PREFIX = String.format("%6s", "REQUEST");

    @Pointcut("within(*..*Controller)")
    public void onRequest() {
    }

    @Around("onRequest()")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info(">>> {} Method:[{}], Path[{}], UUID:[{}], remoteAddr:[{}]",
            REQUEST_PREFIX,
            httpServletRequest.getMethod(),
            httpServletRequest.getServletPath(),
            MDC.get("uuid"),
            MDC.get("remoteAddr"));

        return joinPoint.proceed();
    }

}
