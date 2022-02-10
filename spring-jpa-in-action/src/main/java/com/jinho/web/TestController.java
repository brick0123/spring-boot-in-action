package com.jinho.web;

import com.jinho.domain.Category;
import com.jinho.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/category/{categoryCode}")
    public void get(@PathVariable Long categoryCode) {
        final Category category = categoryRepository.findByCode(categoryCode)
            .orElseThrow(IllegalArgumentException::new);

        log.info(">>> category = [{}]", category);
    }
}
