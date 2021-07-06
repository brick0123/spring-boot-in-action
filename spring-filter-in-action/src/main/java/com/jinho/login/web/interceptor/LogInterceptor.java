package com.jinho.login.web.interceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        log.info("REQUEST [{}][{}][{}] (parameter = {})", uuid, requestURI, handler, parameterMapToString(request.getParameterMap()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uuid = (String) request.getAttribute(LOG_ID);
        String requestURI = request.getRequestURI();
        log.info("RESPONSE [{}][{}][{}]", uuid, requestURI, handler);

    }

    private String parameterMapToString(Map<String, String[]> parameterMap) {
        return parameterMap.keySet().stream()
            .map(key -> key + "=" + Arrays.toString(parameterMap.get(key)))
            .collect(Collectors.joining(", ", "{", "}"));
    }
}