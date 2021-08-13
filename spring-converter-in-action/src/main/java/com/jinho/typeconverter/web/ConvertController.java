package com.jinho.typeconverter.web;

import com.jinho.typeconverter.type.Fruit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ConvertController {

    @GetMapping("/convert/fruit")
    public Fruit get(
        @RequestParam Fruit color
    ) {
        log.info(">>> fruit = {}", color);
        return color;
    }

}
