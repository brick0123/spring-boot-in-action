package com.jinho.clib;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibTest {

    @Test
    @DisplayName("소문자로 변경해주는 Cglib 프록시")
    void simple_lowercase() {

        final Human human = (Human) Enhancer.create(
            Human.class,
            new LowercaseAdvice(new Human())
        );

        final String message = human.greet("JIN HO");
        assertThat(message).isEqualTo("hi jin ho");
    }


    static class Human {

        String greet(final String name) {
            return "Hi " + name;
        }

        int age() {
            return 5;
        }
    }

    static class LowercaseAdvice implements MethodInterceptor {

        private final Object target;

        public LowercaseAdvice(final Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(final Object o, final Method method, final Object[] objects, final MethodProxy methodProxy) throws Throwable {
            return ((String) method.invoke(target, objects)).toLowerCase();
        }
    }

}
