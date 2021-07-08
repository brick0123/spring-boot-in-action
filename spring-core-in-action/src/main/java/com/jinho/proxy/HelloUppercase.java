package com.jinho.proxy;

/**
 *  프록시
 *  경우에 따라 대문자로 출력이  필요한 경우를 위해
 *  HelloUppercase를 통해 문자를 바꿔준다.
 *  Hello 인터페이스를 구현하고, Hello 타입의 타깃 오브젝트를 받아서 저장한다.
 */
public class HelloUppercase implements Hello{

    private final Hello hello;

    public HelloUppercase(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase();
    }
}
