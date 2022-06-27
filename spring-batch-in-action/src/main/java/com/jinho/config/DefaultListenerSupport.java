package com.jinho.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

@Slf4j
public class DefaultListenerSupport extends RetryListenerSupport {

    @Override
    public <T, E extends Throwable> void onError(final RetryContext context, final RetryCallback<T, E> callback, final Throwable throwable) {
        log.error(">> retry context = {}", context);
        super.onError(context, callback, throwable);
    }
}
