package com.jinho.proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;

class HelloTargetTest {

    @Test
    void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Jinho")).isEqualTo("Hello Jinho");

        Hello proxyHello = new HelloUppercase(hello);
        assertThat(proxyHello.sayHello("Jinho")).isEqualTo("HELLO JINHO");
    }

    @Test
    void create_proxy() {
        Hello proxyHello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(), // ㄷ동적으로 생성되는 다이나믹 프록시 클래스의 로딩에 사용될 클래스로더
            new Class[]{Hello.class},
            new UppercaseHandler(new HelloTarget()));
    }

}
