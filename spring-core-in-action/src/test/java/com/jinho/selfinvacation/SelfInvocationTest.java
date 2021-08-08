package com.jinho.selfinvacation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

class SelfInvocationTest {

    @Test
    @DisplayName("pojo를 만들어서 직접 호출한다")
    void direct_pojo() {
        final Pojo pojo = new SimplePojo();
        // direct method call on the 'pojo' reference
        pojo.foo();
    }

    @Test
    @DisplayName("proxy를 이용해서 호출한다")
    void proxy() {
        final ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addInterface(Pojo.class);
        factory.addAdvice(new ExecuteLoggingAdvice());

        final Pojo pojo = (Pojo) factory.getProxy();
        pojo.foo();
    }
}
