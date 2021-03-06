package com.jinho.factorybean;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.proxy.Hello;
import com.jinho.proxy.HelloTarget;
import com.jinho.proxy.UppercaseHandler;
import java.lang.reflect.Proxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class DynamicProxyTest {

    @Test
    void simpleProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{Hello.class},
            new UppercaseHandler(new HelloTarget()));
    }

    @Test
    void proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());
        // 기존 JDK 프록시랑은 다르게 Advice에는 타깃 오브젝트가 등장하지 않는다.

        Hello proxiedHello = (Hello) proxyFactoryBean.getObject(); // 생성된 프록시를 가져온다.

        assert proxiedHello != null;
        assertThat(proxiedHello.sayHello("Jinho")).isEqualTo("HELLO JINHO");
        assertThat(proxiedHello.sayHi("Jinho")).isEqualTo("HI JINHO");
        assertThat(proxiedHello.sayThankYou("Jinho")).isEqualTo("THANK YOU JINHO");
    }

    @Test
    void pointcutAdvisor() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut(); // 메서드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트컷
        pointcut.setMappedName("sayH*");

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) proxyFactoryBean.getObject();

        assert proxiedHello != null;
        assertThat(proxiedHello.sayHello("Jinho")).isEqualTo("HELLO JINHO");
        assertThat(proxiedHello.sayHi("Jinho")).isEqualTo("HI JINHO");
        assertThat(proxiedHello.sayThankYou("Jinho")).isEqualTo("Thank you Jinho");
    }

    // MethodInterceptor는 타깃 오브젝트의 메서드를 실행할 수 있는 기능이 있기 떄문에
    // MethodInterceptor는 부가기능을 제공하는 데만 집중할 수 있다.
    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            assert ret != null;
            return ret.toUpperCase();
        }
    }
}
