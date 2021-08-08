package com.jinho.selfinvacation;

import lombok.extern.slf4j.Slf4j;

/**
 * https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-core
 */
@Slf4j
public class SimplePojo implements Pojo {

    @Override
    public void foo() {
        log.info("### foo");
        this.bar();
    }

    @Override
    public void bar() {
        log.info("### bar");
    }
}
