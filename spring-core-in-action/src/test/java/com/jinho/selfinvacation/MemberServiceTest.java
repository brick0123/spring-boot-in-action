package com.jinho.selfinvacation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void self_invocation() {
        assertThat(Enhancer.isEnhanced(memberService.getClass())).isTrue();
        memberService.doSomething(new Member("member1"));
    }
}
