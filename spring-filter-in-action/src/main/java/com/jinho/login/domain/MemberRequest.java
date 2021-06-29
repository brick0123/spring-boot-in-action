package com.jinho.login.domain;

import lombok.Getter;

@Getter
public class MemberRequest {

    private String name;
    private String password;

    public Member toEntity() {
        return new Member(name, password);
    }
}
