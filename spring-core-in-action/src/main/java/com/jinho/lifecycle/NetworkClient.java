package com.jinho.lifecycle;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetworkClient {

    private String url;

    public NetworkClient() {
        log.info(">>> 생성자 호출 url = [{}]", url);
        connect();
        call("초기화 연결 메세지");
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void connect() {
        log.info(">>> connect: [{}]", url);
    }

    public void call(final String message) {
        log.info(">>> call: [{}] message = [{}]", url, message);
    }

    public void disconnect() {
        log.info(">>> close: [{}]", url);
    }

    @PostConstruct
    public void init() {
        connect();
        call("의존관계 주입 끝");
    }
}
