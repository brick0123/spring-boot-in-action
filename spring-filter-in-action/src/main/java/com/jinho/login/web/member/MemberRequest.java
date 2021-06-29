package com.jinho.login.web.member;

import com.jinho.login.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {

    private String name;
    private String password;

    public Member toEntity() {
        return new Member(name, password);
    }
}
