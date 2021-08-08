package com.jinho.selfinvacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void doSomething(final Member member) {
        saveMember(member);
    }

    @Transactional
    public void saveMember(final Member member) {
        memberRepository.save(member);
    }
}
