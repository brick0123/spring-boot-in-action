package com.jinho.proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HelloTargetTest {

    @Test
    void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Jinho")).isEqualTo("Hello Jinho");

        Hello proxyHello = new HelloUppercase(hello);
        assertThat(proxyHello.sayHello("Jinho")).isEqualTo("HELLO JINHO");
    }

}
