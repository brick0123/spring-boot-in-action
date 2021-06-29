package com.jinho.login.domain.login;

import com.jinho.login.domain.member.Member;
import com.jinho.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String name, String password) {
        return memberRepository.findByName(name)
            .filter(m -> m.getPassword().equals(password))
            .orElseThrow(() -> new IllegalArgumentException("아아디 또는 비밀번호가 맞지 않습니다."));
    }

}
