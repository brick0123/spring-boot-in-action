package com.jinho.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    void name() {
        final ApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        final NetworkClient client = ac.getBean(NetworkClient.class);
        System.out.println("network");
    }

    // 컨테이너 생성 -> 빈 생성 -> 의존 관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            final NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://localhost:8080");
            return networkClient;
        }

    }
}
