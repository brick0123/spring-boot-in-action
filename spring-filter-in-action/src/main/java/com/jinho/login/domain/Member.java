package com.jinho.login.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "id")
public class Member {

    private Long id;
    private String name;
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public Member(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
