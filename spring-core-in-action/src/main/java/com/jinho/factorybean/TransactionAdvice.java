package com.jinho.factorybean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * TxProxyFactoryBean을 스프링이 제공하는 ProxyFactoryBean으로 변경
 */
@Component
public class TransactionAdvice implements MethodInterceptor {

    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * @param invocation : 타깃을 호출하는 기능을 가진 콜백 오브젝트를 프록시로부터 받는다.
     *                   덕분에 어드바이스는 특정 타깃에 의존하지 않고 재사용 가능하다.
     *                   리플렉션을 통한 타깃 메서드 호출 작업을 MethodInvocation 타입의 콜백을 이용해서 간결하게 이용
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object ret = invocation.proceed(); // 콜백을 호출해서 타깃의 메서드를 실행한다. 타깃 메서드 호출 전후로 필요한 부가기능을 넣울 수 있다.
                                               // 경우에 따라서 타깃이 아예 호출되지 않게 하거나 재시도를 위한 반복적인 호출도 가능하다.
            transactionManager.commit(status);
            return ret;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
