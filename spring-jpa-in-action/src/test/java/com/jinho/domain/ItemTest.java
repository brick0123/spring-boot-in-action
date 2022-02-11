package com.jinho.domain;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ItemTest {

    @Autowired
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @Transactional
    void t1() {
        final Item book = Book.builder().price(1000).author("jake").build();
        itemRepository.save(book);

        em.flush();
        em.clear();

        final Item findItem = itemRepository.findById(book.getId()).get();
        System.out.println("findItem = " + findItem);
    }
}
