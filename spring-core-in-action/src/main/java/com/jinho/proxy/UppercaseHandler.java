package com.jinho.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    private final Object target;

    public UppercaseHandler(Object target) {
        this.target = target;
    }

    /**
     * 다이나믹 프록시가 클라이언트로부터 받는 모든 요청은 해당 메서드로 전달된다.
     * 다이나믹 프록시를 통해 요청이 전달되면 리플렉션을 이용해 타깃 오브젝트의 메서드를 호출한다.
     * 타깃 오브젝트는 생성자를 통해 미리 전달 받아 둔다.
     * Hello 인터페이스의 모든 메서드는 결과가 String타입이므로 메서드 호출 결과를 String을 반환해도 안전하다.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);
        if (ret instanceof String && method.getName().startsWith("say")) {
            return ((String) ret).toUpperCase();
        }
        return ret;
    }
}
