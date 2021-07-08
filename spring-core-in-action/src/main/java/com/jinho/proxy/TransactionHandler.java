package com.jinho.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionHandler implements InvocationHandler {

    private final Object target; // 부가 기능을 제공할 타깃 오브젝트. 어떤 타입의 오브젝트도 적용 가능하다
    private final PlatformTransactionManager transactionManager;
    private final String pattern; // 트랜잭션을 적용할 이름 패턴

    public TransactionHandler(Object target, PlatformTransactionManager transactionManager, String pattern) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith(pattern)) {
            return invokeInTransaction(method, args);
        }
        return method.invoke(target, args);
    }

    private Object invokeInTransaction(Method method, Object[] args) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object ret = method.invoke(target, args);
            transactionManager.commit(status);
            return ret;
        } catch (InvocationTargetException | IllegalAccessException e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }
        return null;
    }
}
