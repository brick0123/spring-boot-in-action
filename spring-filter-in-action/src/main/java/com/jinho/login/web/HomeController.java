package com.jinho.login.web;

import com.jinho.login.domain.member.Member;
import com.jinho.login.web.argumentresolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeLogin(@Login Member loginMember, Model model) {
        if (loginMember == null) {
            return "index";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
