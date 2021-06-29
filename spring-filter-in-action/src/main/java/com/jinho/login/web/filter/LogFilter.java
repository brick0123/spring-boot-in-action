package com.jinho.login.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        log.info("REQUEST [{}][{}] (parameter={})", uuid, requestURI, parameterMapToString(request.getParameterMap()));

        ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, wrapper);

        log.info("RESPONSE [{}][{}]", uuid, getResponseBody(wrapper));
    }

    private String parameterMapToString(Map<String, String[]> parameterMap) {
        return parameterMap.keySet().stream()
            .map(key -> key + "=" + Arrays.toString(parameterMap.get(key)))
            .collect(Collectors.joining(", ", "{", "}"));
    }

    private String getResponseBody(final ContentCachingResponseWrapper wrapper) throws IOException {
        String payload = "";
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return payload.isEmpty() ? " - " : payload;
    }
}
