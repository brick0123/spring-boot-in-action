package com.jinho.login.web;

import com.jinho.login.domain.MemberRepository;
import com.jinho.login.domain.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") MemberRequest member) {
        return "members/addMemberForm";
    }


    @PostMapping("/add")
    public String save(MemberRequest request) {
        memberRepository.save(request.toEntity());
        return "redirect:/";
    }

}
