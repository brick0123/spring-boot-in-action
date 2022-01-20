package com.jinho.domain;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HumanService {

    private final HumanRepository humanRepository;

    @Transactional
    @PostConstruct
    public void setup() {
        humanRepository.save(new Human("hello"));
    }

    @Cacheable(cacheNames = "humanService::find", key = "#name")
    @Transactional(readOnly = true)
    public Human getHuman(String name) {
        return humanRepository.findByName(name)
            .orElseThrow(IllegalArgumentException::new);
    }

}
