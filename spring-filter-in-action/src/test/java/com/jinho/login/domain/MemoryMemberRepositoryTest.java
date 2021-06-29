package com.jinho.login.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.login.domain.member.Member;
import com.jinho.login.domain.member.MemberRepository;
import com.jinho.login.domain.member.MemoryMemberRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

    MemberRepository memberRepository;

    @BeforeEach
    void beforeAll() {
        memberRepository = new MemoryMemberRepository();
    }

    @Test
    void save() {
        Member member = new Member("woodcock", "a1234!!");
        Member saveMember = memberRepository.save(member);

        assertThat(saveMember.getId()).isNotNull();
        assertThat(member).isEqualTo(saveMember);
    }

    @Test
    void find() {
        Member member = new Member("woodcock", "a1234!!");
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId())
            .orElseThrow(IllegalStateException::new);

        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findAll() {
        int count = 10;
        for (int i = 0; i < count; i++) {
            Member member = new Member("woodcock", "a1234!!" + count);
            memberRepository.save(member);
        }

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(count);
    }
}
