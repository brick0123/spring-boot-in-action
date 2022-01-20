package com.jinho;

import com.jinho.domain.Human;
import com.jinho.domain.HumanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final HumanService humanService;

    @GetMapping("")
    public String test(@RequestParam String name) {
        final Human human = humanService.getHuman(name);
        return human.getName();
    }
}
