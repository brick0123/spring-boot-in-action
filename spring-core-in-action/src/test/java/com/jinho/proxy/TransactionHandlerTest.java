package com.jinho.proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

class TransactionHandlerTest {

    @Test
    void create_dynamic_proxy() {
        HelloTarget target = new HelloTarget();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();

        TransactionHandler handler = new TransactionHandler(target, transactionManager, "say");

        Hello hello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{Hello.class},
            handler);
    }

}
