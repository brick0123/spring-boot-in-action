package com.jinho;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class CustomLoggingInterceptor implements HandlerInterceptor {

    private static final String REMOTE_ADDR_HEADER_NAME = "X-Forwarded-For";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        final String uuid = UUID.randomUUID().toString();

        String remoteAddr = request.getHeader(REMOTE_ADDR_HEADER_NAME);

        if (StringUtils.hasText(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }

        MDC.put("requestUUID", uuid);
        MDC.put("remoteAddr", remoteAddr);

        return true;
    }
}
